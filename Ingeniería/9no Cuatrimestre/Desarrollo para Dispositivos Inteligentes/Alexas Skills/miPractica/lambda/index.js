/* *
 * This sample demonstrates handling intents from an Alexa skill using the Alexa Skills Kit SDK (v2).
 * Please visit https://alexa.design/cookbook for additional examples on implementing slots, dialog management,
 * session persistence, api calls, and more.
 * */
const Alexa = require('ask-sdk-core');

const LaunchRequestHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'LaunchRequest';
    },
    handle(handlerInput) {
        const speakOutput = 'Bienvenido, ¿En qué te puedo ayudar?';

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
        const speakOutput = 'Hola mundo desde mi práctica!';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//HoraIntent
const HoraIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'HoraIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'No tiene pila mi reloj';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//CreateSpendIntent
const CreateSpendIntent = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'CreateSpendIntent';
    },
    handle(handlerInput) {
        const slots = handlerInput.requestEnvelope.request.intent.slots;
        
        let speakOutput = 'Voy a registrar un gasto de ';

        
        if(slots.monto.value){
            speakOutput = speakOutput + slots.monto.value + 'pesos';
        } else {
            speakOutput = 'No cuento con el monto del gasto'
        }
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//ClimaIntent
const ClimaIntent = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'ClimaIntent';
    },
    handle(handlerInput) {
        const slots = handlerInput.requestEnvelope.request.intent.slots;
        
        const city = slots.city.value;
        const date = slots.date.value;

        let speakOutput = '';

        // Obtener la fecha de hoy
        const today = new Date().toISOString().split('T')[0];

        // Generar una temperatura aleatoria entre 20 y 40 grados
        const temperature = Math.floor(Math.random() * 21) + 20;

        
        // Genera el estado del clima aleatorio
        const climate = [
            'Soleado', 
            'Parcialmente soleado', 
            'Nublado', 
            'Parcialmente nublado', 
            'Lluvia', 
            'Lluvia fuerte', 
            'Lluvia ligera'
        ];
        
        const weatherTime = climate[Math.floor(Math.random() * climate.length)];
        
          // Validar la fecha
            if (date === today) {
                speakOutput = `El clima de hoy en ${city} es de ${temperature} grados y está ${weatherTime}.`;
            } else {
                speakOutput = `El clima en ${city} para la fecha ${date} es de ${temperature} grados y estará ${weatherTime}.`;
            }

        
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//RecomendarLibroIntent
const RecomendarLibroIntent = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'RecomendarLibroIntent';
    },
    handle(handlerInput) {
        
        const speakOutput = 'Te recomiendo el libro de ';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
}

// Conjunto de categorías válidas
const categoriasValidas = ['niño', 'adulto', 'infantil'];

//ReservarVuelosIntent
const ReservarVuelosIntent = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'ReservarVuelosIntent';
    },
    handle(handlerInput) {
        const { requestEnvelope } = handlerInput;
        const intent = requestEnvelope.request.intent;
        const cantidadBoletos = intent.slots.cantidad.value;
        const categoria = intent.slots.persona.value;

        let speakOutput = '';

        if (!categoriasValidas.includes(categoria)) {
            speakOutput = `La categoría ${categoria} no es válida para la reserva de boletos. Por favor, elige entre niño, adulto o infantil.`;
        } else if ((cantidadBoletos === '1') && categoria) {
            speakOutput = `Has reservado ${cantidadBoletos} boleto para la categoría ${categoria}.`;
        } else if ((cantidadBoletos > 1) && categoria) {
            speakOutput = `Has reservado ${cantidadBoletos} boletos para la categoría ${categoria}.`;
        } else {
            speakOutput = 'No he podido obtener la cantidad de boletos o la categoría. Por favor, inténtalo de nuevo.';
        }
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
}

//SendDataIntent
const SendDataIntent = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'SendDataIntent';
    },
    handle(handlerInput) {
        
        const slots = handlerInput.requestEnvelope.request.intent.slots;
        const {intent} = handlerInput.requestEnvelope.request;
        
        if(slots.valorx.value !== "abc"){
            return handlerInput.responseBuilder
            .speak('El valor ingresado no es válido, dime otro')
            .addElicitSlotDirective("valorx")
            .getResponse()
        }
        
        if(intent.confirmationStatus==='NONE'){
            let mensajeConfirmacion = '¿Deseas realizar la acción?';
            return handlerInput.responseBuilder
            .speak(mensajeConfirmacion)
            .addConfirmIntentDirective()
            .getResponse();
        } else if (intent.confirmationStatus==='CONFIRMED'){
            let mensajeOK = '¡He realizado la acción!';
            return handlerInput.responseBuilder
            .speak(mensajeOK)
            .getResponse();
        } else {
            let mensajeFail = '¡No he realizado nada!';
            return handlerInput.responseBuilder
            .speak(mensajeFail)
            .getResponse();
        }
        
        
        let speakOutput = 'Voy a guardar el valor: ' + slots.valorx.value;
        console.log("Aqui papá");

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

const HelpIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.HelpIntent';
    },
    handle(handlerInput) {
        const speakOutput = 'You can say hello to me! How can I help?';

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
        HoraIntentHandler,
        CreateSpendIntent,
        ClimaIntent,
        RecomendarLibroIntent,
        ReservarVuelosIntent,
        SendDataIntent,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        FallbackIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler)
    .addErrorHandlers(
        ErrorHandler)
    .withCustomUserAgent('sample/hello-world/v1.2')
    .lambda();