import forge from "node-forge";


const key = "el@ConGr3z041!12";

export const encrypt = (data) => {
    const jsonString = JSON.stringify(data);

    const cipher = forge.cipher.createCipher("AES-GCM", key);

    const iv = forge.random.getBytesSync(12); // GCM recommends 12 bytes
    cipher.start({iv});

    cipher.update(forge.util.createBuffer(jsonString, "utf8"));
    cipher.finish();

    const encryptedData = forge.util.encode64(iv + cipher.output.getBytes() + cipher.mode.tag.getBytes());
    return encodeURIComponent(encryptedData);
};

export const decrypt = (data) => {
    const decodedEncryptedData = forge.util.decode64(decodeURIComponent(data));

    const iv = decodedEncryptedData.slice(0, 12); // GCM recommends 12 bytes
    const tag = decodedEncryptedData.slice(decodedEncryptedData.length - 16, decodedEncryptedData.length); // GCM tag is 16 bytes
    const encryptedBytes = decodedEncryptedData.slice(12, decodedEncryptedData.length - 16);

    const encryptedBuffer = forge.util.createBuffer(encryptedBytes, "raw");

    const decipher = forge.cipher.createDecipher("AES-GCM", key);

    decipher.start({iv: iv, tag: tag});

    decipher.update(encryptedBuffer);

    if (!decipher.finish()) {
        throw new Error('Authentication failed. The given data could not be decrypted.');
    }

    const decryptedString = decipher.output.toString("utf8");

    return JSON.parse(decryptedString);
}