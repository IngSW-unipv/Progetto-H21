const types = [
   {
      name: "CPU",
      sortOrder: 0
   },
   {
      name: "Motherboard",
      sortOrder: 2
   },
   {
      name: "RAM",
      sortOrder: 1
   },
   {
      name: "CASE",
      sortOrder: 3
   }
];

const families = [
   {
      name: "AMD",
      type: "CPU"
   },
   {
      name: "Intel",
      type: "CPU"
   },
   {
      name: "ASUS",
      type: "Motherboard"
   },
   {
      name: "FIRE",
      type: "Motherboard"
   },
   {
      name: "DDR3",
      type: "RAM"
   },
   {
      name: "DDR4",
      type: "RAM"
   },
   {
      name: "MINI",
      type: "CASE"
   },
   {
      name: "STANDARD",
      type: "CASE"
   }
];

const compatibilityConstraints = [
   {
      family1: "AMD",
      family2: "DDR3"
   },
   {
      family1: "Intel",
      family2: "DDR3"
   },
   {
      family1: "Intel",
      family2: "DDR4"
   },
   {
      family1: "DDR3",
      family2: "FIRE"
   },
   {
      family1: "DDR3",
      family2: "ASUS"
   },
   {
      family1: "DDR4",
      family2: "ASUS"
   },
   {
      family1: "ASUS",
      family2: "MINI"
   },
   {
      family1: "FIRE",
      family2: "STANDARD",
   }
]

const components = [
   {
      name: "Ryzen 5",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "AMD"
   },
   {
      name: "Ryzen 7",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "AMD"
   },
   {
      name: "i5",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "Intel"
   },
   {
      name: "i7",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "Intel"
   },
   {
      name: "MB ALU",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "ASUS"
   },
   {
      name: "MB BLISS",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "FIRE"
   },
   {
      name: "MB BLOWN",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "FIRE"
   },
   {
      name: "8 GB",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "DDR3"
   },
   {
      name: "8 GB",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "DDR4"
   },
   {
      name: "16 GB",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "DDR3"
   },
   {
      name: "16 GB",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "DDR4"
   },
   {
      name: "case Mini",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "MINI"
   },
   {
      name: "case Standard",
      price: Math.random() * 100,
      powerSupply: Math.random() * 20,
      family: "STANDARD"
   }
]

export { types, families, compatibilityConstraints, components};