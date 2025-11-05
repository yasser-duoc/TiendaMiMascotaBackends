import swaggerAutogen from "swagger-autogen";
const doc = {
 info: {
 title: "API de TiendaMiMascota",
 description: "Documentación de la API de TiendaMiMascota de un servicio node.js",
 },
 host: "localhost:3000",
 schemes: ["http"],
};
const outputFile = "./swagger-output.json";
const endpointsFiles = ["./index.js"];
swaggerAutogen()(outputFile, endpointsFiles, doc);