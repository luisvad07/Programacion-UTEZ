/* *
 * This sample demonstrates handling intents from an Alexa skill using the Alexa Skills Kit SDK (v2).
 * Please visit https://alexa.design/cookbook for additional examples on implementing slots, dialog management,
 * session persistence, api calls, and more.
 * */
const Alexa = require('ask-sdk-core');
const AWS = require('aws-sdk');
const ddbAdapter = require('ask-sdk-dynamodb-persistence-adapter');

// Configura DynamoDB
//const dynamoDbClient = new AWS.DynamoDB.DocumentClient({ apiVersion: 'latest', region: process.env.DYNAMODB_PERSISTENCE_REGION });

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
                speakOutput = 'Bienvenido al Sistema de Recetas. ¿En que te puedo ayudar?';
            } else {
                attributesManager.setPersistentAttributes({});
                await attributesManager.savePersistentAttributes();
                console.log('New user attributes saved');
                speakOutput = '¡Eres nuevo en el Sistema de Recetas!. ¿En qué te puedo ayudar?';
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
        const speakOutput = 'Hola mundo desde la receta!';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

// Intents Handlers

const CrearRecetaIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'CrearRecetaIntent';
    },
    async handle(handlerInput) {
        const { nombreReceta, ingredientes, instrucciones } = handlerInput.requestEnvelope.request.intent.slots;
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Creando la receta.';

        // Validar que los campos estén presentes
        if (!nombreReceta.value || !ingredientes.value || !instrucciones.value) {
            speakOutput = 'Por favor proporciona todos los detalles de la receta, incluyendo nombre, ingredientes e instrucciones.';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Asegúrate de proporcionar el nombre, los ingredientes y las instrucciones de la receta.')
                .getResponse();
        }

        const receta = {
            nombreReceta: nombreReceta.value,
            ingredientes: ingredientes.value,
            instrucciones: instrucciones.value
        };

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];
            
            // Verificar si la receta ya existe
            const existingRecipe = recetas.find(r => r.nombreReceta === receta.nombreReceta);
            if (existingRecipe) {
                speakOutput = `La receta de ${nombreReceta.value} ya está registrada.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt('Por favor, proporciona un nombre de receta diferente.')
                    .getResponse();
            }

            recetas.push(receta);
            attributes.recetas = recetas;
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();
            speakOutput = `Receta de ${nombreReceta.value} creada con éxito.`;
        } catch (error) {
            console.error('Error saving recipe:', error);
            speakOutput = 'Lo siento, hubo un problema al crear la receta. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const LeerRecetaIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'LeerRecetaIntent';
    },
    async handle(handlerInput) {
        const nombreReceta = Alexa.getSlotValue(handlerInput.requestEnvelope, 'nombreReceta');
        const attributesManager = handlerInput.attributesManager;
        
        let speakOutput = 'Buscando la receta.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];
            
            // Buscar la receta
            const receta = recetas.find(r => r.nombreReceta === nombreReceta);
            
            if (receta) {
                const imageUrl = 'https://asassyspoon.com/wp-content/uploads/cuban-style-black-beans-frijoles-negro-4-edited-783x900.jpg'; // Imagen estática
                speakOutput = `La receta de ${nombreReceta} tiene los ingredientes: ${receta.ingredientes}. Instrucciones: ${receta.instrucciones}.`;
                
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .withStandardCard('Receta', `Ingredientes: ${receta.ingredientes}. Instrucciones: ${receta.instrucciones}`, imageUrl)
                    .getResponse();
            } else {
                speakOutput = `No encontré la receta para ${nombreReceta}.`;
                
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .withStandardCard('Receta no encontrada', speakOutput, 'https://previews.123rf.com/images/thodonal/thodonal1805/thodonal180500683/102076220-ilustraci%C3%B3n-de-un-concepto-de-sistema-de-error.jpg')  // URL de imagen por defecto en caso de error
                    .getResponse();
            }
        } catch (error) {
            console.error('Error retrieving recipe:', error);
            speakOutput = 'Lo siento, hubo un problema al recuperar la receta. Por favor, intenta de nuevo más tarde.';
            
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .getResponse();
        }
    }
};

const ActualizarRecetaIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'ActualizarRecetaIntent';
    },
    async handle(handlerInput) {
        const nombreReceta = Alexa.getSlotValue(handlerInput.requestEnvelope, 'nombreReceta');
        const ingredientes = Alexa.getSlotValue(handlerInput.requestEnvelope, 'ingredientes');
        const instrucciones = Alexa.getSlotValue(handlerInput.requestEnvelope, 'instrucciones');
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Actualizando la receta.';

        // Validar que los campos estén presentes
        if (!nombreReceta || !ingredientes || !instrucciones) {
            speakOutput = 'Por favor proporciona todos los detalles de la receta, incluyendo nombre, ingredientes e instrucciones.';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Asegúrate de proporcionar el nombre, los ingredientes y las instrucciones de la receta.')
                .getResponse();
        }

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];

            // Buscar y actualizar la receta
            const recetaIndex = recetas.findIndex(r => r.nombreReceta === nombreReceta);
            if (recetaIndex === -1) {
                speakOutput = `No se encontró la receta de ${nombreReceta}.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .getResponse();
            }

            // Actualizar la receta
            recetas[recetaIndex] = {
                nombreReceta,
                ingredientes,
                instrucciones
            };

            attributes.recetas = recetas;
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();

            speakOutput = `Receta ${nombreReceta} actualizada con éxito.`;
        } catch (error) {
            console.error('Error updating recipe:', error);
            speakOutput = 'Lo siento, hubo un problema al actualizar la receta. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const EliminarRecetaIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'EliminarRecetaIntent';
    },
    async handle(handlerInput) {
        const nombreReceta = Alexa.getSlotValue(handlerInput.requestEnvelope, 'nombreReceta');
        const attributesManager = handlerInput.attributesManager;

        let speakOutput = 'Eliminando la receta.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];

            // Buscar y eliminar la receta
            const recetaIndex = recetas.findIndex(r => r.nombreReceta === nombreReceta);
            if (recetaIndex === -1) {
                speakOutput = `No se encontró la receta de ${nombreReceta}.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .getResponse();
            }

            // Eliminar la receta
            recetas.splice(recetaIndex, 1);

            attributes.recetas = recetas;
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();

            speakOutput = `Receta ${nombreReceta} eliminada con éxito.`;
        } catch (error) {
            console.error('Error deleting recipe:', error);
            speakOutput = 'Lo siento, hubo un problema al eliminar la receta. Por favor, intenta de nuevo más tarde.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const DeleteAllRecipesIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'DeleteAllRecipesIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Eliminando todos los registros de recetas.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];

            if (recetas.length === 0) {
                speakOutput = 'No hay recetas para eliminar.';
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .getResponse();
            }

            // Eliminar todas las recetas
            attributes.recetas = [];
            attributesManager.setPersistentAttributes(attributes);
            await attributesManager.savePersistentAttributes();

            speakOutput = 'Todos los registros de recetas han sido eliminados.';
        } catch (error) {
            console.error('Error deleting all recipes:', error);
            speakOutput = 'Hubo un error al intentar eliminar los registros de recetas.';
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .getResponse();
    }
};

const VisualizarTodasLasRecetasIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'VisualizarTodasLasRecetasIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        let speakOutput = 'Obteniendo todas las recetas.';

        try {
            const attributes = await attributesManager.getPersistentAttributes() || {};
            const recetas = attributes.recetas || [];

            if (recetas.length === 0) {
                speakOutput = 'No tienes recetas guardadas en este momento.';
            } else {
                // Crear una lista de recetas
                const recetaList = recetas.map(receta => 
                    `Receta de ${receta.nombreReceta} con ingredientes: ${receta.ingredientes}. Instrucciones: ${receta.instrucciones}.`
                ).join(' ');

                speakOutput = `Aquí están tus recetas: ${recetaList}`;
            }
        } catch (error) {
            console.error('Error retrieving recipes:', error);
            speakOutput = 'Hubo un problema al intentar recuperar las recetas. Por favor, intenta de nuevo más tarde.';
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
        const speakOutput = 'Puedes pedirme información sobre recetas, como los ingredientes y las instrucciones. También puedes preguntarme sobre calificaciones de estudiantes. ¿Cómo puedo ayudarte hoy?';
        const imageUrl = 'https://st3.depositphotos.com/13324256/18639/i/450/depositphotos_186397504-stock-photo-help.jpg';  // Reemplaza con la URL de tu imagen de ayuda
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .withStandardCard('Ayuda', 'Puedes pedirme información sobre recetas, como los ingredientes y las instrucciones. También puedes preguntarme sobre calificaciones de estudiantes.', imageUrl)
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
        const speakOutput = 'Goodbye!';

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
        const speakOutput = 'Sorry, I don\'t know about that. Please try again.';

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
        const speakOutput = `You just triggered ${intentName}`;

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
        const speakOutput = 'Sorry, I had trouble doing what you asked. Please try again.';
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
        CrearRecetaIntentHandler,
        LeerRecetaIntentHandler,
        ActualizarRecetaIntentHandler,
        EliminarRecetaIntentHandler,
        DeleteAllRecipesIntentHandler,
        VisualizarTodasLasRecetasIntentHandler,
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
    .withCustomUserAgent('sample/hello-world/v1.2')
    .lambda();