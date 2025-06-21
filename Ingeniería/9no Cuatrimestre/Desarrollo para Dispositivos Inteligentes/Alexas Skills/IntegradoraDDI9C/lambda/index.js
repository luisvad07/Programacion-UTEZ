/* *
 * This sample demonstrates handling intents from an Alexa skill using the Alexa Skills Kit SDK (v2).
 * Please visit https://alexa.design/cookbook for additional examples on implementing slots, dialog management,
 * session persistence, api calls, and more.
 * */
const Alexa = require('ask-sdk-core');
const AWS = require('aws-sdk');
const ddbAdapter = require('ask-sdk-dynamodb-persistence-adapter');
const i18next = require('i18next');

// Configure i18next
i18next.init({
  lng: 'es', // Default language
  resources: {
    en: {
      translation: {
        'LaunchRequest': 'Welcome to the Employee System, how can I assist you?',
        'newUser': 'You are new to the Employee System, Welcome! How can I assist you?',
        'error': 'Sorry, there was a problem retrieving or saving your data. Please try again later.',
        'HelloWorldIntent': 'Hello world from the project system',
      }
    },
    es: {
      translation: {
        'LaunchRequest': '¡Bienvenido al Sistema de Empleados!, ¿En qué te puedo ayudar?',
        'newUser': 'Eres nuevo en el Sistema de Empleados, ¡Bienvenido!, ¿En qué te puedo ayudar?',
        'HelloWorldIntent': 'hola mundo desde el sistema de proyectos',
      }
    }
  }
});

const getLanguageFromRequest = (handlerInput) => {
  // Get the language from the request (e.g., 'es' or 'en')
  return handlerInput.requestEnvelope.request.locale.split('-')[0];
};

const LaunchRequestHandler = {
  canHandle(handlerInput) {
    return Alexa.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
  },
  async handle(handlerInput) {
    console.log('LaunchRequestHandler invoked');

    const language = getLanguageFromRequest(handlerInput);
    i18next.changeLanguage(language);

    let speakOutput;
    const attributesManager = handlerInput.attributesManager;
    
    try {
      const attributes = await attributesManager.getPersistentAttributes() || {};
      console.log('Persistent attributes fetched:', attributes);

      if (Object.keys(attributes).length > 0) {
        speakOutput = i18next.t('LaunchRequest');
      } else {
        attributesManager.setPersistentAttributes({});
        await attributesManager.savePersistentAttributes();
        console.log('New user attributes saved');
        speakOutput = i18next.t('newUser');
      }
    } catch (error) {
      console.error('Error fetching or saving attributes:', error);
      speakOutput = i18next.t('error');
    }
    
    console.log('Response:', speakOutput); // Debugging output

    return handlerInput.responseBuilder
      .speak(speakOutput)
      .reprompt(speakOutput)
      .getResponse();
  }
};

const HelloWorldIntentHandler = {
  canHandle(handlerInput) {
    return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
        && Alexa.getIntentName(handlerInput.requestEnvelope) === 'HelloWorldIntent';
  },
  handle(handlerInput) {
    const language = getLanguageFromRequest(handlerInput);
    i18next.changeLanguage(language);

    const speakOutput = i18next.t('HelloWorldIntent');

    return handlerInput.responseBuilder
      .speak(speakOutput)
      .reprompt('add a reprompt if you want to keep the session open for the user to respond')
      .getResponse();
  }
};

const RegisterEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'RegisterEmployeeIntent';
    },
    async handle(handlerInput) {
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;

        const nombre = slots.nombre.value;
        const RFC = slots.RFC.value;
        const sueldo = slots.sueldo.value;
        const puesto = slots.puesto.value;
        
        const positionResolutions = slots.puesto.resolutions;
        const positionResolutionsPerAuthority = positionResolutions && positionResolutions.resolutionsPerAuthority && positionResolutions.resolutionsPerAuthority[0];
        const positionStatusCode = positionResolutionsPerAuthority && positionResolutionsPerAuthority.status.code;

        const rfcPattern = /^[A-Za-z0-9]{13}$/;
        let speakOutput;

        if (!rfcPattern.test(RFC)) {
            speakOutput = 'El RFC proporcionado no es válido.';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('El RFC proporcionado no es válido.')
                .getResponse();
        }

        if (positionStatusCode === 'ER_SUCCESS_NO_MATCH') {
            speakOutput = `El puesto ${puesto} no es válido.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt(`El puesto ${puesto} no es válido.`)
                .getResponse();
        }
        
        if (!nombre || !RFC || isNaN(sueldo) || !puesto) {
            speakOutput = 'Faltan detalles necesarios para registrar el empleado.';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Faltan detalles necesarios para registrar el empleado.')
                .getResponse();
        }

        try {
            const attributesManager = handlerInput.attributesManager;
            const attributes = await attributesManager.getPersistentAttributes() || {};
            if (!attributes.employees) {
                attributes.employees = [];
            }

            const existingEmployee = attributes.employees.find(emp => emp.RFC === RFC);
            if (existingEmployee) {
                speakOutput = `El empleado con RFC ${RFC} ya está registrado.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt(`El empleado con RFC ${RFC} ya está registrado.`)
                    .getResponse();
            }

            const employee = {
                nombre: nombre,
                RFC: RFC,
                sueldo: sueldo,
                puesto: puesto,
                status: 'active'
            };

            attributes.employees.push(employee);
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();
            speakOutput = `Empleado ${nombre} con RFC ${RFC}, sueldo de $${sueldo} pesos y puesto ${puesto} registrado exitosamente.`;
        } catch (error) {
            console.error('Error saving employee:', error);
            speakOutput = 'Lo siento, hubo un problema al registrar el empleado.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const CreateProjectIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'CreateProjectIntent';
    },
    async handle(handlerInput) {
        const { Proyecto, Tipo, acronimo } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        const validTypes = ['interno', 'externo'];  // Tipos de proyectos válidos
        const newAcronimo = acronimo.value.toLowerCase();
        const newTipo = Tipo.value.toLowerCase();
        
        let speakOutput;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];
            
            const acronimoExists = projects.some(proj => proj.acronimo.toLowerCase() === newAcronimo);
            
            if (acronimoExists) {
                speakOutput = `El proyecto con acrónimo ${acronimo.value} ya existe.`;
            } else if (!validTypes.includes(newTipo)) {
                speakOutput = `El tipo de proyecto ${Tipo.value} no es válido.`;
            } else {
                const today = new Date();
                const formattedDate = today.toLocaleDateString('es-ES');

                const project = {
                    proyecto: Proyecto.value,
                    tipo: Tipo.value,
                    acronimo: acronimo.value,
                    status: 'in progress',
                    creationDate: formattedDate
                };

                projects.push(project);
                attributes.projects = projects;
                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
                speakOutput = `Proyecto ${Proyecto.value} de tipo ${Tipo.value} con acrónimo ${acronimo.value} creado exitosamente el ${formattedDate}.`;
            }
        } catch (error) {
            console.error('Error saving project:', error);
            speakOutput = 'Lo siento, hubo un problema al crear el proyecto.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const RegisterTechnologyIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
               Alexa.getIntentName(handlerInput.requestEnvelope) === 'RegisterTechnologyIntent';
    },
    async handle(handlerInput) {
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;

        const technology = slots.technology.value;
        const resolutions = slots.technology.resolutions;
        
        const resolutionsPerAuthority = resolutions && resolutions.resolutionsPerAuthority && resolutions.resolutionsPerAuthority[0];
        const statusCode = resolutionsPerAuthority && resolutionsPerAuthority.status.code;
        
        let speakOutput;

        if (statusCode === 'ER_SUCCESS_NO_MATCH') {
            speakOutput = `La tecnología ${technology} no es válida.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt(speakOutput)
                .getResponse();
        }

        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = await attributesManager.getPersistentAttributes() || {};
        let userTechnologies = sessionAttributes.technologies || [];

        const existingTechnology = userTechnologies.find(tech => tech.name.toLowerCase() === technology.toLowerCase());
        if (existingTechnology) {
            speakOutput = `La tecnología ${technology} ya está registrada con el ID ${existingTechnology.id}.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .getResponse();
        }

        const technologyId = userTechnologies.length + 1;
        const technologyRecord = { id: technologyId, name: technology };

        userTechnologies.push(technologyRecord);
        sessionAttributes.technologies = userTechnologies;
        attributesManager.setPersistentAttributes(sessionAttributes);
        await attributesManager.savePersistentAttributes();

        speakOutput = `Tecnología ${technology} registrada exitosamente con el ID ${technologyId}.`;
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

/*Todos los GET de las tablas*/

const GetEmployeeRecordsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetEmployeeRecordsIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Estos son los registros de empleados: ';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            if (employees.length === 0) {
                speakOutput += 'No hay registros de empleados.';
            } else {
                employees.forEach(employee => {
                    speakOutput += `${employee.nombre}, `;
                });
            }
        } catch (error) {
            console.error('Error fetching employee records:', error);
            speakOutput = 'Hubo un error al obtener los registros de empleados.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const GetProjectRecordsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetProjectRecordsIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Estos son los registros de proyectos: ';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];

            if (projects.length === 0) {
                speakOutput += 'No hay registros de proyectos.';
            } else {
                projects.forEach(project => {
                    speakOutput += `Acrónimo ${project.acronimo}, `;
                });
            }
        } catch (error) {
            console.error('Error fetching project records:', error);
            speakOutput = 'Hubo un error al obtener los registros de proyectos.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const GetTechnologyRecordsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
               Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetTechnologyRecordsIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Estos son los registros de tecnologías: ';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const technologies = attributes.technologies || [];

            if (technologies.length === 0) {
                speakOutput += 'No hay registros de tecnologías.';
            } else {
                technologies.forEach(technology => {
                    speakOutput += `Tecnología: ${technology.name}. `;
                });
            }
        } catch (error) {
            console.error('Error fetching technology records:', error);
            speakOutput = 'Hubo un error al obtener los registros de tecnologías.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const GetSingleEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetSingleEmployeeIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;
        
        const employeeName = nombre.value;
        const employeeRFC = RFC.value;
        
        let speakOutput = `Buscando empleado con nombre ${employeeName} y RFC ${employeeRFC}: `;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            const employee = employees.find(emp => emp.nombre === employeeName && emp.RFC === employeeRFC);

            if (employee) {
                speakOutput += `Empleado encontrado: Nombre: ${employee.nombre}, RFC: ${employee.RFC}, Sueldo: ${employee.sueldo}, Puesto: ${employee.puesto}.`;
            } else {
                speakOutput += `No se encontró el empleado con nombre ${employeeName} y RFC ${employeeRFC}.`;
            }
        } catch (error) {
            console.error('Error fetching single employee record:', error);
            speakOutput = 'Hubo un error al obtener los datos del empleado.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

//Intents Parte 1

const QueryEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'QueryEmployeeIntent';
    },
    async handle(handlerInput) {
        const { acronimo } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Obteniendo información de los empleados asignados al proyecto.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];

            const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.value.toLowerCase());

            if (project) {
                if (project.employees && project.employees.length > 0) {
                    speakOutput = `Empleados asignados al proyecto ${project.acronimo}: ${project.employees.join(', ')}.`;
                } else {
                    speakOutput = `No hay empleados asignados al proyecto ${project.acronimo}.`;
                }
            } else {
                speakOutput = `No se encontró el proyecto con acrónimo ${acronimo.value}.`;
            }
        } catch (error) {
            console.error('Error al obtener la información de los empleados del proyecto:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener la información de los empleados del proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const AssignEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'AssignEmployeeIntent';
    },
    async handle(handlerInput) {
        const { Empleado, RFC, acronimoProyecto } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Asignando empleado al proyecto.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const employee = employees.find(emp => 
                emp.nombre === Empleado.value && emp.RFC === RFC.value
            );

            const newAcronimoProyecto = acronimoProyecto.value.toLowerCase();
            const project = projects.find(proj => proj.acronimo.toLowerCase() === newAcronimoProyecto);

            if (employee && project) {
                const previousProject = projects.find(proj => proj.employees && proj.employees.includes(employee.nombre) && proj.acronimo.toLowerCase() !== newAcronimoProyecto);
                if (previousProject) {
                    previousProject.employees = previousProject.employees.filter(emp => emp !== employee.nombre);
                    previousProject.status = 'Reasignado';
                }

                if (!project.employees) {
                    project.employees = [];
                }
                project.employees.push(employee.nombre);

                const today = new Date();
                const formattedDate = today.toLocaleDateString('es-ES');
                if (!project.assignmentDates) {
                    project.assignmentDates = {};
                }
                project.assignmentDates[employee.nombre] = formattedDate;

                project.status = 'Activo';

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
                speakOutput = `Empleado ${employee.nombre} asignado al proyecto ${project.proyecto} (${project.acronimo}) exitosamente el día ${formattedDate}.`;
            } else {
                if (!employee) {
                    speakOutput = `No se encontró al empleado ${Empleado.value} con RFC ${RFC.value}.`;
                } else if (!project) {
                    speakOutput = `No se encontró el proyecto con acrónimo ${acronimoProyecto.value}.`;
                } else {
                    speakOutput = 'Lo siento, no se pudo encontrar el empleado o el proyecto.';
                }
            }
        } catch (error) {
            console.error('Error al asignar empleado al proyecto:', error);
            speakOutput = 'Lo siento, hubo un problema al asignar el empleado al proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const DeactivateEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeactivateEmployeeIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput;
        const today = new Date();
        const formattedDate = `${today.getDate().toString().padStart(2, '0')}/${(today.getMonth() + 1).toString().padStart(2, '0')}/${today.getFullYear()}`;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const employee = employees.find(emp => 
                emp.nombre.toLowerCase() === nombre.value.toLowerCase() &&
                emp.RFC.toLowerCase() === RFC.value.toLowerCase()
            );

            if (employee) {
                employee.status = 'inactive';
                employee.deactivationDate = formattedDate;
                attributesManager.setPersistentAttributes(attributes);

                let projectsToFinalize = 0;
                const projectHistory = projects.filter(project => project.employees && project.employees.includes(employee.nombre));

                projects.forEach(project => {
                    if (project.employees && project.employees.includes(employee.nombre)) {
                        project.employees = project.employees.filter(empName => empName !== employee.nombre);

                        if (project.employees.length === 0) {
                            if (projectsToFinalize < 2 && projectHistory.length > 20) {
                                project.status = 'Finalizado';
                                projectsToFinalize++;
                            } else if (projectsToFinalize >= 2 && projectHistory.length > 20) {
                                project.status = project.status || 'Activo';
                            } else {
                                project.status = 'Finalizado';
                            }
                        }
                    }
                });

                await attributesManager.savePersistentAttributes();
                speakOutput = `El empleado ${employee.nombre} con RFC ${employee.RFC} ha sido desactivado el día ${formattedDate}.`;
            } else {
                speakOutput = `No se encontró al empleado ${nombre.value} con RFC ${RFC.value}.`;
            }
        } catch (error) {
            console.error('Error al desactivar al empleado:', error);
            speakOutput = 'Lo siento, hubo un problema al desactivar al empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const ReactivateEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'ReactivateEmployeeIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput;
        const today = new Date();
        const formattedDate = `${today.getDate().toString().padStart(2, '0')}/${(today.getMonth() + 1).toString().padStart(2, '0')}/${today.getFullYear()}`;

        const validResources = ['tecnológico', 'industrial', 'financiero', 'humano', 'material'];

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const employee = employees.find(emp => 
                emp.nombre.toLowerCase() === nombre.value.toLowerCase() &&
                emp.RFC.toLowerCase() === RFC.value.toLowerCase()
            );

            if (employee) {
                employee.status = 'active';
                employee.reactivationDate = formattedDate;
                attributesManager.setPersistentAttributes(attributes);

                projects.forEach(project => {
                    if (project.employees && project.employees.includes(employee.nombre)) {
                        project.status = 'Activo';
                    }
                });

                if (!attributes.resources) {
                    attributes.resources = [];
                }

                const randomType = validResources[Math.floor(Math.random() * validResources.length)];

                const newResource = {
                    nombre: employee.nombre,
                    RFC: employee.RFC,
                    tipo: randomType,
                    fechaAsignacion: formattedDate
                };

                attributes.resources.push(newResource);

                await attributesManager.savePersistentAttributes();
                speakOutput = `El empleado ${employee.nombre} con RFC ${employee.RFC} ha sido reactivado el día ${formattedDate} y se le ha asignado el recurso ${randomType}.`;
            } else {
                speakOutput = `No se encontró al empleado ${nombre.value} con RFC ${RFC.value}.`;
            }
        } catch (error) {
            console.error('Error al reactivar al empleado:', error);
            speakOutput = 'Lo siento, hubo un problema al reactivar al empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const AssignResourceIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'AssignResourceIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC, tipoRecurso } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Asignando el recurso al proyecto.';

        const validResources = ['tecnológico', 'industrial', 'financiero', 'humano', 'material'];

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const employee = employees.find(emp => 
                emp.nombre.toLowerCase() === nombre.value.toLowerCase() &&
                emp.RFC.toLowerCase() === RFC.value.toLowerCase()
            );

            if (employee) {
                if (validResources.includes(tipoRecurso.value.toLowerCase())) {
                    if (!attributes.resources) {
                        attributes.resources = [];
                    }

                    const newResource = {
                        nombre: employee.nombre,
                        RFC: employee.RFC,
                        tipo: tipoRecurso.value,
                        fechaAsignacion: new Date().toLocaleDateString('es-ES')
                    };

                    attributes.resources.push(newResource);

                    await attributesManager.savePersistentAttributes();
                    speakOutput = `Recurso ${tipoRecurso.value} asignado al proyecto exitosamente.`;
                } else {
                    speakOutput = `El recurso ${tipoRecurso.value} no es válido. Los recursos válidos son: ${validResources.join(', ')}.`;
                }
            } else {
                speakOutput = `No se encontró al empleado ${nombre.value} con RFC ${RFC.value}.`;
            }
        } catch (error) {
            console.error('Error al asignar recurso:', error);
            speakOutput = 'Lo siento, hubo un problema al asignar el recurso al proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const ProjectReportIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'ProjectReportIntent';
    },
    async handle(handlerInput) {
        const acronimo = handlerInput.requestEnvelope.request.intent.slots.acronimo.value;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Consultando el estado del proyecto.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];
            const employees = attributes.employees || [];

            const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.toLowerCase());

            if (project) {
                const today = new Date();
                const generateFutureDate = (daysToAdd) => {
                    const futureDate = new Date(today);
                    futureDate.setDate(today.getDate() + daysToAdd);
                    return futureDate.toISOString();
                };

                if (!project.startDate) {
                    project.startDate = generateFutureDate(Math.floor(Math.random() * 30) + 1);
                }

                if (!project.finalizationDate) {
                    project.finalizationDate = generateFutureDate(Math.floor(Math.random() * 60) + 31);
                }

                const calculateDaysBetweenDates = (startDate, endDate) => {
                    const start = new Date(startDate);
                    const end = new Date(endDate);
                    return Math.ceil((end - start) / (1000 * 60 * 60 * 24));
                };

                const projectDuration = calculateDaysBetweenDates(project.startDate, project.finalizationDate);

                const formatDate = date => {
                    if (!date) return 'No disponible';
                    const d = new Date(date);
                    return d.toLocaleDateString('es-ES'); 
                };

                const technologies = project.technologies ? project.technologies.join(', ') : 'No hay tecnologías involucradas.';

                let resourcesDetails = project.resources ? project.resources.join(', ') : 'No hay recursos asignados.';
                let costDetails = 'Costos no disponibles.';

                if (project.employees) {
                    costDetails = project.employees.map(employeeName => {
                        const employee = employees.find(emp => emp.nombre === employeeName);
                        if (employee) {
                            const percentage = (project.assignmentDates && project.assignmentDates[employeeName]) ? (1 / project.employees.length) * 100 : 0;
                            const costPerDay = (employee.sueldo / 30) * (percentage / 100);
                            const totalCost = costPerDay * projectDuration;
                            return `${employeeName} con sueldo de ${employee.sueldo}, asignado al ${percentage}% del proyecto. Costo total estimado: $${totalCost.toFixed(2)} `;
                        }
                        return `${employeeName} (información del sueldo no disponible)`;
                    }).join('; ');
                }

                speakOutput = `Estado del proyecto con acrónimo ${project.acronimo}: Proyecto: ${project.proyecto}, Tipo: ${project.tipo}, Estado: ${project.status}. Tecnologías involucradas: ${technologies}. Recursos asignados: ${resourcesDetails}. Costo total estimado de los empleados: ${costDetails}. Fecha de inicio: ${formatDate(project.startDate)}, Fecha de finalización: ${formatDate(project.finalizationDate)}.`;
            } else {
                speakOutput = `No se encontró un proyecto con el acrónimo ${acronimo}.`;
            }
        } catch (error) {
            console.error('Error al generar el reporte del proyecto:', error);
            speakOutput = 'Lo siento, hubo un problema al generar el reporte del proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const CompleteProjectIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'CompleteProjectIntent';
    },
    async handle(handlerInput) {
        const { acronimo } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];
            const employees = attributes.employees || [];

            const projectIndex = projects.findIndex(proj => proj.acronimo.toLowerCase() === acronimo.value.toLowerCase());

            if (projectIndex > -1) {
                const project = projects[projectIndex];
                project.status = 'Finalizado';
                project.finalizationDate = new Date().toISOString();

                if (project.employees) {
                    project.employees.forEach(employeeName => {
                        const employee = employees.find(emp => emp.nombre === employeeName);
                        if (employee) {
                            employee.activeProjects = employee.activeProjects.filter(projAcronimo => projAcronimo !== acronimo.value);
                        }
                    });
                }

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
                speakOutput = `El proyecto con acrónimo ${acronimo.value} ha sido marcado como finalizado.`;
            } else {
                speakOutput = `No se encontró un proyecto con el acrónimo ${acronimo.value}.`;
            }
        } catch (error) {
            console.error('Error completing project:', error);
            speakOutput = 'Lo siento, hubo un problema al completar el proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const SearchCandidatesIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'SearchCandidatesIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Buscando empleados disponibles para proyectos.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const activeProjects = projects.filter(proj => proj.status === 'Activo');
            const assignedEmployees = activeProjects.flatMap(proj => proj.employees || []);
            const availableEmployees = employees.filter(emp => !assignedEmployees.includes(emp.nombre) && emp.status === 'active');

            if (availableEmployees.length > 0) {
                speakOutput = `Se encontraron los siguientes empleados disponibles para proyectos: ${availableEmployees.map(emp => emp.nombre).join(', ')}.`;
            } else {
                speakOutput = 'No se encontraron empleados disponibles para proyectos.';
            }
        } catch (error) {
            console.error('Error searching available employees for projects:', error);
            speakOutput = 'Lo siento, hubo un problema al buscar empleados disponibles. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const EmployeeStatusIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'EmployeeStatusIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = `Obteniendo la información del empleado con nombre ${nombre.value} y RFC ${RFC.value}.`;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            const employee = employees.find(emp => emp.nombre.toLowerCase() === nombre.value.toLowerCase() && emp.RFC === RFC.value);

            if (employee) {
                const employeeProjects = employee.activeProjects ? employee.activeProjects.join(', ') : 'No asignado a ningún proyecto.';
                speakOutput = `El empleado ${employee.nombre} con RFC ${employee.RFC} está trabajando en los siguientes proyectos: ${employeeProjects}.`;
            } else {
                speakOutput = `El empleado ${nombre.value} con RFC ${RFC.value} no está asignado a ningún proyecto actualmente.`;
            }
        } catch (error) {
            console.error('Error fetching employee status:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener el estado del empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

//Intents Parte 2 (Aquí multi)

const DeleteEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteEmployeeIntent';
    },
    async handle(handlerInput) {
        const { nombre, RFC } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput;

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            const employeeIndex = employees.findIndex(emp => emp.nombre.toLowerCase() === nombre.value.toLowerCase() && emp.RFC.toLowerCase() === RFC.value.toLowerCase());

            if (employeeIndex > -1) {
                const [deletedEmployee] = employees.splice(employeeIndex, 1);
                attributes.employees = employees;

                // Actualizar los proyectos en los que el empleado estaba asignado
                projects.forEach(project => {
                    if (project.employees && project.employees.includes(deletedEmployee.nombre)) {
                        project.employees = project.employees.filter(empName => empName !== deletedEmployee.nombre);
                        if (project.employees.length === 0) {
                            project.status = 'Finalizado'; // Marcar el proyecto si ya no tiene empleados
                        }
                    }
                });

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
                speakOutput = `El empleado ${nombre.value} con RFC ${RFC.value} ha sido eliminado del sistema.`;
            } else {
                speakOutput = `No se encontró al empleado con nombre ${nombre.value} y RFC ${RFC.value}.`;
            }
        } catch (error) {
            console.error('Error deleting employee:', error);
            speakOutput = 'Lo siento, hubo un problema al eliminar al empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const UpdateEmployeeIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'UpdateEmployeeIntent';
    },
    async handle(handlerInput) {
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;
        
        // Obtener los valores de los slots y las resoluciones
        const nombre = slots.nombre.value;
        const RFC = slots.RFC.value;
        const newNombre = slots.newNombre.value;
        const newRFC = slots.newRFC.value;
        const newSueldo = slots.newSueldo.value;
        const newPuesto = slots.newPuesto.value;
        const resolutions = slots.newPuesto.resolutions;

        // Verificar la resolución del slot
        const resolutionsPerAuthority = resolutions && resolutions.resolutionsPerAuthority && resolutions.resolutionsPerAuthority[0];
        const statusCode = resolutionsPerAuthority && resolutionsPerAuthority.status.code;

        // Validación de RFC
        const rfcPattern = /^[A-Za-z0-9]{13}$/;
        if (newRFC && !rfcPattern.test(newRFC)) {
            return handlerInput.responseBuilder
                .speak('El nuevo RFC proporcionado no es válido. Debe ser alfanumérico y tener 13 dígitos.')
                .reprompt('Por favor, proporciona un nuevo RFC válido.')
                .getResponse();
        }

        // Confirmación de slots
        if (handlerInput.requestEnvelope.request.dialogState !== 'COMPLETED') {
            if (slots.newNombre && slots.newNombre.confirmationStatus !== 'CONFIRMED') {
                return handlerInput.responseBuilder
                    .addConfirmSlotDirective('newNombre')
                    .speak(`El nuevo nombre del empleado es ${newNombre}. ¿Es correcto?`)
                    .getResponse();
            }

            if (slots.newRFC && slots.newRFC.confirmationStatus !== 'CONFIRMED') {
                return handlerInput.responseBuilder
                    .addConfirmSlotDirective('newRFC')
                    .speak(`El nuevo RFC del empleado es ${newRFC}. ¿Es correcto?`)
                    .getResponse();
            }

            return handlerInput.responseBuilder
                .addDelegateDirective()
                .getResponse();
        }

        // Verificar si el nuevo puesto es válido
        if (statusCode === 'ER_SUCCESS_NO_MATCH') {
            const speakOutput = `Lo siento, el nuevo puesto ${newPuesto} no es válido o no está reconocido. Por favor, intenta con otro puesto.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt(speakOutput)
                .getResponse();
        }

        let speakOutput = 'Actualizando los datos del empleado.';

        try {
            const attributesManager = handlerInput.attributesManager;
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            // Verificar si el nuevo RFC ya está en uso
            const rfcInUse = employees.some(emp => emp.RFC === newRFC && emp.nombre !== nombre);
            if (rfcInUse) {
                return handlerInput.responseBuilder
                    .speak(`El nuevo RFC ${newRFC} ya está asociado a otro empleado. Por favor, proporciona un RFC diferente.`)
                    .reprompt('Por favor, proporciona un RFC diferente.')
                    .getResponse();
            }

            const employee = employees.find(emp => emp.nombre === nombre && emp.RFC === RFC);

            if (employee) {
                // Actualizar detalles del empleado
                employee.nombre = newNombre || employee.nombre;
                employee.RFC = newRFC || employee.RFC;
                employee.sueldo = newSueldo || employee.sueldo;
                employee.puesto = newPuesto || employee.puesto;
                employee.status = 'active';

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
                speakOutput = `El empleado ${nombre} ha sido actualizado exitosamente.`;
            } else {
                speakOutput = `No se encontró al empleado ${nombre} con RFC ${RFC}.`;
            }
        } catch (error) {
            console.error('Error updating employee:', error);
            speakOutput = 'Lo siento, hubo un problema al actualizar los datos del empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const StartProjectIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'StartProjectIntent';
    },
    async handle(handlerInput) {
        const { acronimo } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Iniciando el proyecto.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];

            const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.value.toLowerCase());

            if (project) {
                // Establecer la fecha de inicio al día de hoy
                const today = new Date();
                project.startDate = today.toISOString();

                // Función para formatear la fecha en formato dd-mm-yyyy
                const formatDate = date => {
                    const d = new Date(date);
                    const day = String(d.getDate()).padStart(2, '0');
                    const month = String(d.getMonth() + 1).padStart(2, '0'); // Meses están en base 0
                    const year = d.getFullYear();
                    return `${day}-${month}-${year}`;
                };

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();

                speakOutput = `La fecha de inicio del proyecto con acrónimo ${project.acronimo} se ha iniciado. Fecha de Inicio: ${formatDate(project.startDate)}.`;
            } else {
                speakOutput = `No se encontró el proyecto con el acrónimo ${acronimo.value}.`;
            }
        } catch (error) {
            console.error('Error starting project:', error);
            speakOutput = 'Lo siento, hubo un problema al actualizar la fecha de inicio del proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const AssignTechnologyIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
               Alexa.getIntentName(handlerInput.requestEnvelope) === 'AssignTechnologyIntent';
    },
    async handle(handlerInput) {
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;
        const acronimo = slots.acronimo.value;
        const technology = slots.technology.value;
        const resolutions = slots.technology.resolutions;

        // Obtener el valor de la tecnología y verificar la resolución del slot
        const resolutionsPerAuthority = resolutions && resolutions.resolutionsPerAuthority && resolutions.resolutionsPerAuthority[0];
        const statusCode = resolutionsPerAuthority && resolutionsPerAuthority.status.code;

        if (statusCode === 'ER_SUCCESS_NO_MATCH') {
            // Si no hay coincidencia, informar al usuario
            const speakOutput = `Lo siento, la tecnología ${technology} no es válida o no está reconocida. Por favor, intenta con otra tecnología.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt(speakOutput)
                .getResponse();
        }

        // Registrar la tecnología en el proyecto
        const attributesManager = handlerInput.attributesManager;
        const attributes = await attributesManager.getPersistentAttributes() || {};
        const projects = attributes.projects || [];
        
        const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.toLowerCase());

        if (project) {
            // Añadir la tecnología al proyecto
            if (!project.technologies) {
                project.technologies = [];
            }

            if (!project.technologies.includes(technology)) {
                project.technologies.push(technology);

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();

                const speakOutput = `La tecnología ${technology} ha sido asignada al proyecto con acrónimo ${project.acronimo}`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .getResponse();
            } else {
                const speakOutput = `La tecnología ${technology} ya está asignada al proyecto con acrónimo ${project.acronimo}.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .getResponse();
            }
        } else {
            const speakOutput = `No se encontró el proyecto con acrónimo ${acronimo}.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .getResponse();
        }
    }
};

const AssignFutureProjectIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'AssignFutureProjectIntent';
    },
    async handle(handlerInput) {
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;
        const { nombre, RFC, acronimo } = slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Asignando al empleado al proyecto.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];
            const employees = attributes.employees || [];

            // Validaciones
            const employee = employees.find(emp => emp.RFC === RFC.value);
            const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.value.toLowerCase());

            // Validar la existencia del empleado
            if (!employee) {
                speakOutput = `No se encontró un empleado con el RFC ${RFC.value}.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt('Por favor, proporciona un RFC válido.')
                    .getResponse();
            }

            // Validar la existencia del proyecto
            if (!project) {
                speakOutput = `No se encontró el proyecto con el acrónimo ${acronimo.value}.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt('Por favor, proporciona un acrónimo válido del proyecto.')
                    .getResponse();
            }

            // Generar una fecha futura aleatoria (entre 1 y 30 días a partir de hoy)
            const today = new Date();
            const randomDaysToAdd = Math.floor(Math.random() * 30) + 1; // Genera un número entre 1 y 30
            const futureDate = new Date(today);
            futureDate.setDate(today.getDate() + randomDaysToAdd);

            // Asignar el empleado al proyecto en la fecha generada
            if (!project.assignmentDates) {
                project.assignmentDates = {};
            }

            project.assignmentDates[nombre.value] = futureDate.toISOString();

            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();

            speakOutput = `Empleado ${nombre.value} ha sido asignado al proyecto ${acronimo.value} a partir del ${futureDate.toLocaleDateString('es-ES')}.`;
        } catch (error) {
            console.error('Error assigning employee to project:', error);
            speakOutput = 'Lo siento, hubo un problema al asignar el empleado al proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const GetEmployeeStatusIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetEmployeeStatusIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo el estado de los empleados y los proyectos en los que han trabajado. ';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];
            const projects = attributes.projects || [];

            if (employees.length === 0) {
                speakOutput += 'No se encontraron registros de empleados.';
            } else {
                employees.forEach(employee => {
                    // Obtener proyectos asociados al empleado
                    const employeeProjects = projects.filter(proj => proj.employees && proj.employees.includes(employee.nombre));
                    const projectList = employeeProjects.map(proj => proj.proyecto).join(', ');

                    speakOutput += `Empleado: ${employee.nombre}, RFC: ${employee.RFC}, Estado: ${employee.status}. `;
                    if (projectList) {
                        speakOutput += `Ha trabajado en los siguientes proyectos: ${projectList}. `;
                    } else {
                        speakOutput += 'No ha trabajado en ningún proyecto. ';
                    }
                });
            }
        } catch (error) {
            console.error('Error fetching employee status:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener el estado de los empleados. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

/*GET Total*/

const GetEmployeeRecordsTotalIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetEmployeeRecordsTotalIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo registros de los empleados existentes en el Sistema. ';
        let cardContent = '';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            if (employees.length === 0) {
                speakOutput += 'No se encontraron registros de empleados.';
                cardContent = 'No hay registros de empleados en el sistema.';
            } else {
                speakOutput += 'Los registros de todos los empleados son: ';
                cardContent = employees.map(employee => `Nombre: ${employee.nombre}, RFC: ${employee.RFC}, Sueldo: $${employee.sueldo} pesos, Puesto: ${employee.puesto}, Estado: ${employee.status}`).join('\n');
            }
        } catch (error) {
            console.error('Error fetching employee records:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener los registros de empleados. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .withSimpleCard('Registros de Empleados', cardContent)
            .getResponse();
    }
};

const GetProjectRecordsTotalIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetProjectRecordsTotalIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo registros de los proyectos existentes en el Sistema. ';
        let cardContent = '';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];

            if (projects.length === 0) {
                speakOutput += 'No se encontraron registros de proyectos.';
                cardContent = 'No hay registros de proyectos en el sistema.';
            } else {
                speakOutput += 'Los registros de todos los proyectos son: ';
                cardContent = projects.map(project => 
                    `Proyecto: ${project.proyecto}, Acrónimo: ${project.acronimo}, Tipo: ${project.tipo}, Estado: ${project.status}, Fecha de Creación: ${project.creationDate}`
                ).join('\n');
            }
        } catch (error) {
            console.error('Error fetching project records:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener los registros de proyectos. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .withSimpleCard('Registros de Proyectos', cardContent)
            .getResponse();
    }
};

const GetTechnologyRecordsTotalIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetTechnologyRecordsTotalIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo registros de las tecnologías existentes en el Sistema. ';
        let cardContent = '';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const technologies = attributes.technologies || [];

            if (technologies.length === 0) {
                speakOutput += 'No se encontraron registros de tecnologías.';
                cardContent = 'No hay registros de tecnologías en el sistema.';
            } else {
                speakOutput += 'Los registros de las tecnologías son: ';
                cardContent = technologies.map(technology => 
                    `Tecnología: ${technology.name} con id: ${technology.id}`
                ).join('\n');
            }
        } catch (error) {
            console.error('Error fetching technology records:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener los registros de tecnologías. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .withSimpleCard('Registros de Tecnologías', cardContent)
            .getResponse();
    }
};

const DeleteTechnologyIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
               Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteTechnologyIntent';
    },
    async handle(handlerInput) {
        const technologyId = parseInt(handlerInput.requestEnvelope.request.intent.slots.TechnologyId.value, 10);

        const attributesManager = handlerInput.attributesManager;
        const persistentAttributes = await attributesManager.getPersistentAttributes() || {};

        if (!persistentAttributes.technologies) {
            persistentAttributes.technologies = [];
        }

        const technologyIndex = persistentAttributes.technologies.findIndex(t => t.id === technologyId);

        if (technologyIndex !== -1) {
            const removedTechnology = persistentAttributes.technologies.splice(technologyIndex, 1)[0];
            attributesManager.setPersistentAttributes(persistentAttributes);
            await attributesManager.savePersistentAttributes();

            const speechText = `La tecnología con ID ${technologyId} que es ${removedTechnology.name} ha sido eliminada.`;
            return handlerInput.responseBuilder
                .speak(speechText)
                .getResponse();
        } else {
            const speechText = `No se encontró ninguna tecnología con el ID ${technologyId}.`;
            return handlerInput.responseBuilder
                .speak(speechText)
                .reprompt('Por favor, verifica el ID de la tecnología e inténtalo de nuevo.')
                .getResponse();
        }
    }
};

const ModifyFutureDateIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'ModifyFutureDateIntent';
    },
    async handle(handlerInput) {
        const { slots } = handlerInput.requestEnvelope.request.intent;
        const acronimo = slots.acronimo.value;
        const dateType = slots.dateType.value.toLowerCase();
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Modificando la fecha del proyecto.';

        if (!['inicio', 'finalización'].includes(dateType)) {
            speakOutput = `El tipo de fecha "${dateType}" no es válido. Por favor, especifica "inicio" o "finalización".`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Por favor, especifica "inicio" o "finalización".')
                .getResponse();
        }

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const projects = attributes.projects || [];
            const project = projects.find(proj => proj.acronimo.toLowerCase() === acronimo.toLowerCase());

            if (project) {
                const today = new Date();
                const generateFutureDate = (daysToAdd) => {
                    const futureDate = new Date(today);
                    futureDate.setDate(today.getDate() + daysToAdd);
                    return futureDate.toISOString();
                };

                let newDate;
                if (dateType === 'inicio') {
                    newDate = generateFutureDate(Math.floor(Math.random() * 30) + 1);
                    project.startDate = newDate;
                    speakOutput = `La fecha de inicio del proyecto ${project.acronimo} ha sido actualizada a ${new Date(newDate).toLocaleDateString('es-ES')}.`;
                } else {
                    newDate = generateFutureDate(Math.floor(Math.random() * 60) + 31);
                    project.finalizationDate = newDate;
                    speakOutput = `La fecha de finalización del proyecto ${project.acronimo} ha sido actualizada a ${new Date(newDate).toLocaleDateString('es-ES')}.`;
                }

                attributesManager.setPersistentAttributes(attributes);
                await attributesManager.savePersistentAttributes();
            } else {
                speakOutput = `No se encontró el proyecto con el acrónimo ${acronimo}.`;
            }
        } catch (error) {
            console.error('Error modifying project date:', error);
            speakOutput = 'Lo siento, hubo un problema al modificar la fecha del proyecto. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const InterestingFactIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'InterestingFactIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const facts = [
            'Ganó el premio al mejor empleado del mes en julio.',
            'Participó en una competencia de habilidades y obtuvo el primer lugar.',
            'Recibió un reconocimiento por completar 5 años en la empresa.',
            'Lideró un proyecto que resultó en un incremento del 20% en las ventas.',
            'Desarrolló una solución que mejoró la eficiencia del equipo en un 30%.'
        ];

        // Seleccionar un dato interesante aleatorio
        const randomFact = facts[Math.floor(Math.random() * facts.length)];
        let speakOutput = `Dato interesante sobre el empleado: ${randomFact}`;

        // URL de la imagen para la tarjeta
        const urlImage = 'https://img.freepik.com/vector-gratis/eleccion-feliz-empleado-concepto-trabajador_23-2148620896.jpg?t=st=1724134715~exp=1724138315~hmac=41b7fe73d88c09a4840208f0faa07ad1eb2d1d6c7feca52862197f2ffc88db08&w=740'; // Reemplaza con el URL de tu imagen

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const employees = attributes.employees || [];

            if (employees.length === 0) {
                speakOutput = 'No hay empleados registrados para mostrar datos interesantes.';
            } else {
                const randomEmployee = employees[Math.floor(Math.random() * employees.length)];
                speakOutput = `Dato interesante sobre ${randomEmployee.nombre}: ${randomFact}`;

                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .withStandardCard('Dato Interesante', speakOutput, urlImage)
                    .getResponse();
            }
        } catch (error) {
            console.error('Error fetching interesting fact:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener un dato interesante sobre el empleado. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const ConfirmDeleteAllIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
               Alexa.getIntentName(handlerInput.requestEnvelope) === 'ConfirmDeleteAllIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Confirmando la eliminación de todos los registros.';

        try {
            // Obtener los atributos persistentes
            const persistentAttributes = await attributesManager.getPersistentAttributes() || {};
            
            // Vaciar todas las listas de registros
            persistentAttributes.employees = [];
            persistentAttributes.projects = [];
            persistentAttributes.technologies = [];
            
            // Guardar los cambios
            attributesManager.setPersistentAttributes(persistentAttributes);
            await attributesManager.savePersistentAttributes();

            speakOutput = 'Todos los registros de empleados, proyectos y tecnologías han sido eliminados.';
        } catch (error) {
            console.error('Error confirming deletion of all records:', error);
            speakOutput = 'Lo siento, hubo un problema al eliminar todos los registros. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

/*Aquí irán los deletes All de las tablas existentes*/

const DeleteAllEmployeesIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteAllEmployeesIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const persistentAttributes = await attributesManager.getPersistentAttributes() || {};

        persistentAttributes.employees = []; // Vacía la lista de empleados

        attributesManager.setPersistentAttributes(persistentAttributes);
        await attributesManager.savePersistentAttributes();

        const speechText = 'Todos los registros de empleados han sido eliminados.';
        return handlerInput.responseBuilder.speak(speechText).getResponse();
    }
};

const DeleteAllProjectsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteAllProjectsIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const persistentAttributes = await attributesManager.getPersistentAttributes() || {};

        persistentAttributes.projects = []; // Vacía la lista de proyectos

        attributesManager.setPersistentAttributes(persistentAttributes);
        await attributesManager.savePersistentAttributes();

        const speechText = 'Todos los registros de proyectos han sido eliminados.';
        return handlerInput.responseBuilder.speak(speechText).getResponse();
    }
};

const DeleteAllTechnologiesIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteAllTechnologiesIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const persistentAttributes = await attributesManager.getPersistentAttributes() || {};

        persistentAttributes.technologies = []; // Vacía la lista de tecnologías

        attributesManager.setPersistentAttributes(persistentAttributes);
        await attributesManager.savePersistentAttributes();

        const speechText = 'Todos los registros de tecnologías han sido eliminados.';
        return handlerInput.responseBuilder.speak(speechText).getResponse();
    }
};

const HelpIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.HelpIntent';
    },
    handle(handlerInput) {
        const speakOutput = '¡Puedes saludarme! ¿Cómo puedo ayudar?';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

const CancelAndStopIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && (Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.CancelIntent'
                || Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.StopIntent');
    },
    handle(handlerInput) {
        const speakOutput = 'Adios!';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};
/* *
 * FallbackIntent triggers when a customer says something that doesn’t map to any intents in your skill
 * It must also be defined in the language model (if the locale supports it)
 * This handler can be safely added but will be ingnored in locales that do not support it yet 
 * */
const FallbackIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.FallbackIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'Lo siento, no sé nada de eso. Inténtalo de nuevo.';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};
/* *
 * SessionEndedRequest notifies that a session was ended. This handler will be triggered when a currently open 
 * session is closed for one of the following reasons: 1) The user says "exit" or "quit". 2) The user does not 
 * respond or says something that does not match an intent defined in your voice model. 3) An error occurs 
 * */
const SessionEndedRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'SessionEndedRequest';
    },
    handle(handlerInput) {
        console.log(`~~~~ Session ended: ${JSON.stringify(handlerInput.requestEnvelope)}`);
        // Any cleanup logic goes here.
        return handlerInput.responseBuilder.getResponse(); // notice we send an empty response
    }
};
/* *
 * The intent reflector is used for interaction model testing and debugging.
 * It will simply repeat the intent the user said. You can create custom handlers for your intents 
 * by defining them above, then also adding them to the request handler chain below 
 * */
const IntentReflectorHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest';
    },
    handle(handlerInput) {
        const intentName = Alexa.getIntentName(handlerInput.requestEnvelope);
        const speakOutput = `Acabas de Activar el Intent ${intentName}`;

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};
/**
 * Generic error handling to capture any syntax or routing errors. If you receive an error
 * stating the request handler chain is not found, you have not implemented a handler for
 * the intent being invoked or included it in the skill builder below 
 * */
const ErrorHandler = {
    canHandle() {
        return true;
    },
    handle(handlerInput, error) {
        const speakOutput = 'Lo siento, obtuve problemas para realizar lo que me pediste. Inténtalo de nuevo.';
        console.log(`~~~~ Error handled: ${JSON.stringify(error)}`);

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

/**
 * This handler acts as the entry point for your skill, routing all request and response
 * payloads to the handlers above. Make sure any new handlers or interceptors you've
 * defined are included below. The order matters - they're processed top to bottom 
 * */
exports.handler = Alexa.SkillBuilders.custom()
    .addRequestHandlers(
        LaunchRequestHandler,
        HelloWorldIntentHandler,
        RegisterEmployeeIntentHandler, 
        CreateProjectIntentHandler,
        RegisterTechnologyIntentHandler,
        GetEmployeeRecordsIntentHandler,
        GetProjectRecordsIntentHandler,
        GetTechnologyRecordsIntentHandler,
        GetSingleEmployeeIntentHandler,
        QueryEmployeeIntentHandler,
        AssignEmployeeIntentHandler,
        DeactivateEmployeeIntentHandler,
        ReactivateEmployeeIntentHandler,
        AssignResourceIntentHandler,
        ProjectReportIntentHandler,
        CompleteProjectIntentHandler,
        SearchCandidatesIntentHandler,
        EmployeeStatusIntentHandler,
        DeleteEmployeeIntentHandler,
        UpdateEmployeeIntentHandler,
        StartProjectIntentHandler,
        AssignTechnologyIntentHandler,
        AssignFutureProjectIntentHandler,
        GetEmployeeStatusIntentHandler,
        GetEmployeeRecordsTotalIntentHandler,
        GetProjectRecordsTotalIntentHandler,
        GetTechnologyRecordsTotalIntentHandler,
        DeleteTechnologyIntentHandler,
        ModifyFutureDateIntentHandler,
        InterestingFactIntentHandler,
        ConfirmDeleteAllIntentHandler,
        DeleteAllEmployeesIntentHandler,
        DeleteAllProjectsIntentHandler,
        DeleteAllTechnologiesIntentHandler,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        FallbackIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler)
    .addErrorHandlers(ErrorHandler)
    .withPersistenceAdapter(
        new ddbAdapter.DynamoDbPersistenceAdapter({
            tableName: process.env.DYNAMODB_PERSISTENCE_TABLE_NAME,
            createTable: false,
            dynamoDBClient: new AWS.DynamoDB({ apiVersion: 'latest', region: process.env.DYNAMODB_PERSISTENCE_REGION })
        })
    )
    .lambda();