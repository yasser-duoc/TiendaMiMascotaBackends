import express from "express";

//Creacion del Router
const router = express.Router();

//Creacion de los Productos
const products = [
    { id: 1,
      codigo: 'COM001',
      nombre: 'Comida para Perros Premium',
      descripcion: 'Alimento balanceado para perros adultos', 
      precio: 15000, 
      stock: 50, 
      stockCritico: 10, 
      categoria: 'Comida', 
      imagen: 'assets/img/Comida.jpg' },
    { id: 2, 
      codigo: 'CAM001', 
      nombre: 'Cama para Mascotas', 
      descripcion: 'Cama cómoda y resistente para mascotas', 
      precio: 25000, 
      stock: 20, 
      stockCritico: 5, 
      categoria: 'Accesorios', 
      imagen: 'assets/img/cama2.png' },
    { id: 3, 
      codigo: 'JUG001', 
      nombre: 'Juguetes Variados', 
      descripcion: 'Set de juguetes para entretenimiento', 
      precio: 8000, 
      stock: 30, 
      stockCritico: 8, 
      categoria: 'Juguetes', 
      imagen: 'assets/img/jugetes.png' },
    { id: 4, 
      codigo: 'HIG001', 
      nombre: 'Productos de Higiene', 
      descripcion: 'Kit completo de higiene para mascotas', 
      precio: 12000, 
      stock: 15, 
      stockCritico: 3, 
      categoria: 'Higiene', 
      imagen: 'assets/img/higiene.png' },
    { id: 5, 
      codigo: 'ACC001', 
      nombre: 'Accesorios para Mascotas', 
      descripcion: 'Variedad de accesorios útiles para el día a día con tu mascota', 
      precio: 18000, 
      stock: 25, 
      stockCritico: 5, 
      categoria: 'Accesorios', 
      imagen: 'assets/img/accesorios.png' },
    { id: 6, 
      codigo: 'SAL001', 
      nombre: 'Productos de Salud', 
      descripcion: 'Vitaminas y suplementos para mantener la salud de tu mascota', 
      precio: 22000, 
      stock: 18, 
      stockCritico: 4, 
      categoria: 'Salud', 
      imagen: 'assets/img/salud.png' },
    { id: 7, 
      codigo: 'PROD001', 
      nombre: 'Producto Especial', 
      descripcion: 'Producto premium para el cuidado especial de mascotas', 
      precio: 35000, 
      stock: 10, 
      stockCritico: 2, 
      categoria: 'Premium', 
      imagen: 'assets/img/prod.png' }
  ]

// Creación de una ruta GET
router.get("/", (req, res) => {
 res.json(products);    
});
// Creacion de una ruta POST
router.post("/", (req, res) => {
  if (req.body.name && req.body.price && req.body.image && req.body.description) {
    const newProduct = {
      id: products.length + 1,
      name: req.body.name,
      price: req.body.price,
      image: req.body.image,
      description: req.body.description,
    };
    products.push(newProduct);

    res.status(201).json(newProduct);
  } else {
    res.status(400).json({ error: "Faltan datos obligatorios" });
  }
});

// exportación del router
export default router;
