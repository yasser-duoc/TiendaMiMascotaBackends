import cors from "cors";
import express from "express";
import swaggerUi from "swagger-ui-express";
import usersRouter from "./src/routes/products.js";
import { readFileSync } from "fs";
const app = express();
app.use(express.json());
app.use(cors({origin: "http://localhost:5173"}));
app.use("/products", usersRouter);
// Importa la documentación generada
const swaggerFile = JSON.parse(readFileSync("./swagger-output.json"));
app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerFile));
const PORT = 3000;
app.listen(PORT, () => console.log(`🚀http://localhost:${PORT}/api-docs`));
