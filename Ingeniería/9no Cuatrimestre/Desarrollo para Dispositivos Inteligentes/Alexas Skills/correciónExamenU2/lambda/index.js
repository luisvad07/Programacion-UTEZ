/* *
 * This sample demonstrates handling intents from an Alexa skill using the Alexa Skills Kit SDK (v2).
 * Please visit https://alexa.design/cookbook for additional examples on implementing slots, dialog management,
 * session persistence, api calls, and more.
 * */
const Alexa = require('ask-sdk-core');
const AWS = require('aws-sdk');
const ddbAdapter = require('ask-sdk-dynamodb-persistence-adapter');

const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
    },
    async handle(handlerInput) {
        console.log('LaunchRequestHandler invoked');
        
        let speakOutput = 'Saludo desde persistencias con DynamoDB';
        const attributesManager = handlerInput.attributesManager;
        
        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            console.log('Persistent attributes fetched:', attributes);

            if (Object.keys(attributes).length > 0) {
                speakOutput = 'Bienvenido al Sistema de Calificaciones. ¿En que te puedo ayudar?';
            } else {
                attributesManager.setPersistentAttributes({});
                await attributesManager.savePersistentAttributes();
                console.log('New user attributes saved');
                speakOutput = '¡Eres nuevo en el Sistema de Calificaciones!. ¿En qué te puedo ayudar?';
            }
        } catch (error) {
            console.error('Error fetching or saving attributes:', error);
            speakOutput = 'Lo siento, obtuve un problema al recuperar o guardar tus datos. Por favor, intenta de nuevo más tarde.';
        }
        
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
        const speakOutput = 'Hola Mundo desde el Sistema de Calificaciones';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//Intents para Registrar

const AddStudentsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'AddStudentsIntent';
    },
    async handle(handlerInput) {
        const { Estudiante, Matricula } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        const matriculaPattern = /^[A-Za-z0-9]{11}$/;
        let speakOutput = `Registrando al Estudiante.`;

        if (!Matricula.value || !matriculaPattern.test(Matricula.value)) {
            speakOutput = 'La Matrícula proporcionada no es válida. Debe ser alfanumérica y tener 11 dígitos.';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Por favor, proporciona una Matrícula válida.')
                .getResponse();
        }

        const student = {
            Estudiante: Estudiante.value,
            Matricula: Matricula.value,
            status: 'Activo'
        };

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const students = attributes.students || [];

            // Verificar si la matrícula ya existe
            const existingStudent = students.find(s => s.Matricula === Matricula.value);
            if (existingStudent) {
                speakOutput = `La Matrícula ${Matricula.value} ya está registrada en el sistema.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt('Por favor, proporciona una Matrícula diferente.')
                    .getResponse();
            }

            students.push(student);
            attributes.students = students;
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();
            speakOutput = `El Estudiante ${Estudiante.value} con Matrícula ${Matricula.value} ha sido registrado exitosamente al Sistema.`;
        } catch (error) {
            console.error('Error saving student:', error);
            speakOutput = 'Lo siento, hubo un problema al registrar al estudiante. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const AddRatingsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'AddRatingsIntent';
    },
    async handle(handlerInput) {
        
        const { request } = handlerInput.requestEnvelope;
        const { slots } = request.intent;

        // Obtener el valor del slot y la resolución
        const matriculaValue = slots.Matricula.value
        const actividadValue = slots.Actividad.value
        const ratingValue = slots.Calificacion.value;
        const resolutions = slots.Calificacion.resolutions;
        
        // Verificar la resolución del slot
        const resolutionsPerAuthority = resolutions && resolutions.resolutionsPerAuthority && resolutions.resolutionsPerAuthority[0];
        const statusCode = resolutionsPerAuthority && resolutionsPerAuthority.status.code;
        
        if (statusCode === 'ER_SUCCESS_NO_MATCH') {
            // Si no hay coincidencia, informar al usuario
            const speakOutput = `Lo siento, la calificación ${ratingValue} no es válida o no está reconocida. Por favor, intenta con otra tecnología.`;
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Porfavor, proporciona una calificación válida')
                .getResponse();
        }

        // Si hay una coincidencia válida, registrar la tecnología
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = await attributesManager.getPersistentAttributes() || {};
        let userRatings = sessionAttributes.ratings || [];
        
        // Generar un identificador único para la calificación
        const id = userRatings.length > 0 ? userRatings[userRatings.length - 1].id + 1 : 1;

        const ratingRecord = { 
            id,
            matricula: matriculaValue, 
            actividad: actividadValue, 
            rating: ratingValue 
        };

        userRatings.push(ratingRecord);
        sessionAttributes.ratings = userRatings;
        attributesManager.setPersistentAttributes(sessionAttributes);
        await attributesManager.savePersistentAttributes();

        const speakOutput = `He registrado la calificación del estudiante con matricula ${matriculaValue} en el Sistema`;
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
        
    }
};

//Intents para los GETS

const GetTotalStudentsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetTotalStudentsIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo registros de los Estudiantes existentes en el Sistema. ';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const students = attributes.students || [];

            if (students.length === 0) {
                speakOutput += 'No se encontraron registros de Estudiantes.';
            } else {
                speakOutput += 'Los registros de todos los Estudiantes son: ';
                students.forEach(student => {
                    speakOutput += `{ Estudiante: ${student.Estudiante}, Matricula: ${student.Matricula} Estado: ${student.status}. }, `;
                });
            }
        } catch (error) {
            console.error('Error fetching employee records:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener los registros de empleados. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const GetRatingsStudentsIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'GetRatingsStudentsIntent';
    },
    async handle(handlerInput) {
        const { Matricula } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Obteniendo información de las calificaciones del Estudiante.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const ratings = attributes.ratings || [];

            // Buscar las calificaciones por matrícula
            const studentRatings = ratings.filter(rating => rating.matricula === Matricula.value);

            if (studentRatings.length > 0) {
                speakOutput = `Las calificaciones del estudiante con matrícula ${Matricula.value} son: `;
                studentRatings.forEach(rating => {
                    speakOutput += `${rating.id}. Actividad ${rating.actividad} con calificacion de ${rating.rating.toUpperCase()}. `;
                });
            } else {
                speakOutput = `No se encontraron calificaciones para el estudiante con matrícula ${Matricula.value}.`;
            }
        } catch (error) {
            console.error('Error fetching student ratings:', error);
            speakOutput = 'Lo siento, hubo un problema al obtener las calificaciones del estudiante. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

//Intents Delete

const DeleteStudentIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteStudentIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Borrando todos los registros de estudiantes.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            attributes.students = []; // Borra todos los estudiantes
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();
            speakOutput = 'Todos los registros de estudiantes han sido borrados exitosamente.';
        } catch (error) {
            console.error('Error clearing students:', error);
            speakOutput = 'Lo siento, hubo un problema al borrar los registros de estudiantes. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const DeleteRatingIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' &&
            Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteRatingIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Borrando todas las calificaciones.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            attributes.ratings = []; // Borra todas las calificaciones
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();
            speakOutput = 'Todas las calificaciones han sido borradas exitosamente.';
        } catch (error) {
            console.error('Error clearing ratings:', error);
            speakOutput = 'Lo siento, hubo un problema al borrar las calificaciones. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
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
        const speakOutput = `Acabas de activar ${intentName}`;

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
        const speakOutput = 'Lo siento, tuve problemas para hacer lo que me pediste. Inténtalo de nuevo.';
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
        AddStudentsIntentHandler,
        AddRatingsIntentHandler,
        GetTotalStudentsIntentHandler,
        GetRatingsStudentsIntentHandler,
        DeleteStudentIntentHandler,
        DeleteRatingIntentHandler,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        FallbackIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler)
    .addErrorHandlers(
        ErrorHandler)
    .withPersistenceAdapter(
        new ddbAdapter.DynamoDbPersistenceAdapter({
            tableName: process.env.DYNAMODB_PERSISTENCE_TABLE_NAME,
            createTable: false,
            dynamoDBClient: new AWS.DynamoDB({ apiVersion: 'latest', region: process.env.DYNAMODB_PERSISTENCE_REGION })
        })
    )
    .withCustomUserAgent('sample/hello-world/v1.2')
    .lambda();