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
        const speakOutput = 'Hello World!';

        return handlerInput.responseBuilder
            .speak(speakOutput)
            //.reprompt('add a reprompt if you want to keep the session open for the user to respond')
            .getResponse();
    }
};

//Info Planetas 
const infoPlanetas = {
    'mercurio': [
        'Mercurio es el planeta más cercano al Sol.',
        'Mercurio no tiene atmósfera significativa.',
        'Un día en Mercurio dura 59 días terrestres.',
        'Mercurio tiene un núcleo de hierro enorme.',
        'La temperatura en Mercurio puede alcanzar hasta 430 grados Celsius.',
        'Mercurio tiene acantilados que se extienden por cientos de kilómetros.',
        'Mercurio tiene una órbita muy excéntrica.',
        'El tamaño de Mercurio es similar al de la Luna de la Tierra.',
        'Mercurio tiene cráteres con hielo en sus polos.',
        'La sonda Messenger estudió Mercurio en detalle.'
    ],
    'venus': [
        'Venus es el planeta más caliente del sistema solar.',
        'Venus tiene una atmósfera densa y tóxica.',
        'Un día en Venus es más largo que un año en Venus.',
        'Venus tiene una rotación retrógrada.',
        'Venus tiene volcanes y montañas altas.',
        'Las nubes de Venus están compuestas de ácido sulfúrico.',
        'Venus es conocido como el gemelo de la Tierra por su tamaño similar.',
        'Venus no tiene lunas.',
        'Las sondas Venera de la Unión Soviética aterrizaron en Venus.',
        'Venus es el segundo planeta desde el Sol.'
    ],
    'tierra': [
        'La Tierra es el único planeta conocido con vida.',
        'La Tierra tiene un campo magnético protector.',
        'El 70% de la superficie de la Tierra está cubierta de agua.',
        'La Tierra tiene una atmósfera rica en oxígeno.',
        'La Tierra tiene una única luna.',
        'La Tierra orbita el Sol a una velocidad de 30 kilómetros por segundo.',
        'La Tierra tiene placas tectónicas en movimiento.',
        'La Tierra tiene estaciones debido a su inclinación axial.',
        'La Tierra es el tercer planeta desde el Sol.',
        'La Tierra se formó hace unos 4.5 mil millones de años.'
    ],
    'marte': [
        'Marte es conocido como el planeta rojo.',
        'Marte tiene la montaña más alta del sistema solar, Olympus Mons.',
        'Marte tiene dos lunas, Fobos y Deimos.',
        'La atmósfera de Marte es muy delgada.',
        'Marte tiene estaciones similares a las de la Tierra.',
        'El rover Curiosity está explorando Marte.',
        'Se cree que hubo agua líquida en Marte en el pasado.',
        'Marte tiene valles y desiertos de arena roja.',
        'Marte es el cuarto planeta desde el Sol.',
        'Marte tiene tormentas de polvo que pueden cubrir todo el planeta.'
    ],
    'jupiter': [
        'Júpiter es el planeta más grande del sistema solar.',
        'Júpiter tiene una gran mancha roja que es una tormenta gigante.',
        'Júpiter tiene al menos 79 lunas.',
        'Júpiter tiene un fuerte campo magnético.',
        'La luna Europa de Júpiter podría tener un océano subterráneo.',
        'Júpiter tiene anillos tenues.',
        'La sonda Juno está estudiando Júpiter.',
        'Júpiter es el quinto planeta desde el Sol.',
        'Júpiter está compuesto principalmente de hidrógeno y helio.',
        'Júpiter tiene una rápida rotación, completando un día en 10 horas.'
    ],
    'saturno': [
        'Saturno es conocido por sus anillos impresionantes.',
        'Saturno es el segundo planeta más grande del sistema solar.',
        'Saturno tiene más de 80 lunas.',
        'La luna Titán de Saturno tiene una atmósfera densa.',
        'Saturno es el sexto planeta desde el Sol.',
        'Los anillos de Saturno están compuestos de hielo y roca.',
        'Saturno tiene una densidad tan baja que flotaría en agua.',
        'La sonda Cassini estudió Saturno durante 13 años.',
        'Saturno tiene una rápida rotación, completando un día en 10.7 horas.',
        'Saturno tiene auroras en sus polos.'
    ],
    'urano': [
        'Urano gira de lado en comparación con los otros planetas.',
        'Urano tiene anillos tenues y oscuros.',
        'Urano es el séptimo planeta desde el Sol.',
        'Urano tiene 27 lunas conocidas.',
        'La atmósfera de Urano contiene metano, que le da su color azul verdoso.',
        'Urano tiene una rotación retrógrada como Venus.',
        'Urano fue descubierto en 1781 por William Herschel.',
        'Urano tiene una temperatura muy baja, alrededor de -224 grados Celsius.',
        'Urano tiene una estructura interna compuesta de hielo y roca.',
        'La sonda Voyager 2 pasó por Urano en 1986.'
    ],
    'neptuno': [
        'Neptuno es el planeta más lejano del Sol.',
        'Neptuno tiene fuertes vientos y tormentas.',
        'Neptuno tiene 14 lunas conocidas.',
        'La luna Tritón de Neptuno tiene géiseres que expulsan nitrógeno.',
        'Neptuno tiene un color azul intenso debido al metano en su atmósfera.',
        'Neptuno fue descubierto en 1846.',
        'Neptuno es el cuarto planeta más grande en diámetro.',
        'Neptuno tiene anillos tenues compuestos de polvo y hielo.',
        'Neptuno tarda 165 años en completar una órbita alrededor del Sol.',
        'La sonda Voyager 2 pasó por Neptuno en 1989.'
    ]
};

const PlanetaIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'PlanetaIntent';
    },
    handle(handlerInput) {
        const planetaSlot = handlerInput.requestEnvelope.request.intent.slots.planeta;
        
        if (planetaSlot && planetaSlot.value) {
            const planetaNom = planetaSlot.value.toLowerCase();
            
            // Verificar resolución de slots
            const resolutions = planetaSlot.resolutions.resolutionsPerAuthority;
            let isResolved = false;

            if (resolutions) {
                for (let resolution of resolutions) {
                    if (resolution.status.code === 'ER_SUCCESS_MATCH') {
                        isResolved = true;
                        break;
                    }
                }
            }

            if (!isResolved) {
                const speakOutput = `Lo siento, no tengo información sobre ${planetaSlot.value}. Por favor, elige otro planeta.`;
                return handlerInput.responseBuilder
                    .speak(speakOutput)
                    .reprompt(speakOutput)
                    .addElicitSlotDirective('planeta')
                    .getResponse();
            }

            // El planeta es válido, proceder con la respuesta
            const datosPlaneta = infoPlanetas[planetaNom];
            const datoAleatorio = datosPlaneta[Math.floor(Math.random() * datosPlaneta.length)];
            const speakOutput = `Elegiste el planeta ${planetaSlot.value}. Un dato interesante: ${datoAleatorio}`;

            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt('Elige otro planeta.')
                .addElicitSlotDirective('planeta')
                .getResponse();
        } else {
            const speakOutput = 'No entendí qué planeta deseas conocer. ¿Podrías repetirlo?';
            return handlerInput.responseBuilder
                .speak(speakOutput)
                .reprompt(speakOutput)
                .addElicitSlotDirective('planeta')
                .getResponse();
        }
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
        PlanetaIntentHandler,
        HelpIntentHandler,
        CancelAndStopIntentHandler,
        FallbackIntentHandler,
        SessionEndedRequestHandler,
        IntentReflectorHandler)
    .addErrorHandlers(
        ErrorHandler)
    .withCustomUserAgent('sample/hello-world/v1.2')
    .lambda();