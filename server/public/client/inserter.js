import { types, families, compatibilityConstraints, components } from './data.js';

let token = "";
let typesUploaded = [];
let familiesUploaded = [];
let componentsUploaded = [];
let compatibilityConstraintsUploaded = [];

const login = () => {
   return new Promise((resolve, reject) => {
      fetch("http://localhost:8080/login", {
         method: "POST",
         body: JSON.stringify({ name: "admin", password: "admin" }),
         headers: { "Content-Type": "application/json" }
      }).then((result) => {
         return result.json()
      }).then((json) => {
         console.log(json);
         token = json["token"];
         console.log(token);
         resolve();
      });
   })
};

const deleteAll = () => {
   return new Promise((resolve, reject) => {
      fetch("http://localhost:8080//deleteAll", {
         method: "DELETE",
         headers: { token: token }
      }).then((result) => {
         return result.json()
      }).then((json) => {
         console.log(json);
         resolve();
      });
   })
}

const recursiveInsertTypes = (index) => {
   index = index || 0;
   return new Promise((resolve, reject) => {
      if (index === types.length) {
         resolve();
      } else {
         fetch("http://localhost:8080/componentType", {
            method: "POST",
            body: JSON.stringify(types[index]),
            headers: { "Content-Type": "application/json", token: token }
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);
            typesUploaded.push(json);
            recursiveInsertTypes(index + 1).then(() => {
               resolve();
            });
         });
      }
   })
}

const recursiveInsertFamilies = (index) => {
   index = index || 0;
   return new Promise((resolve, reject) => {
      if (index === families.length) {
         resolve();
      } else {
         const typeId = typesUploaded.filter((item) =>
            item.name === families[index].type)[0].id;
         const element = {
            name: families[index].name,
            typeId: typeId
         }
         fetch("http://localhost:8080/componentFamily", {
            method: "POST",
            body: JSON.stringify(element),
            headers: { "Content-Type": "application/json", token: token }
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);
            familiesUploaded.push(json);
            recursiveInsertFamilies(index + 1).then(() => {
               resolve();
            });
         });
      }
   })
}

const recursiveInsertComponents = (index) => {
   index = index || 0;
   return new Promise((resolve, reject) => {
      if (index === components.length) {
         resolve();
      } else {
         const familyId = familiesUploaded.filter((item) =>
            item.name === components[index].family)[0].id;
         const element = {
            name: components[index].name,
            familyId: familyId,
            price: components[index].price,
            powerSupply: components[index].powerSupply
         }
         fetch("http://localhost:8080/component", {
            method: "POST",
            body: JSON.stringify(element),
            headers: { "Content-Type": "application/json", token: token }
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);
            componentsUploaded.push(json);
            recursiveInsertComponents(index + 1).then(() => {
               resolve();
            });
         });
      }
   })
}

const recursiveInsertCompatibilityConstraints = (index) => {
   index = index || 0;
   return new Promise((resolve, reject) => {
      if (index === compatibilityConstraints.length) {
         resolve();
      } else {
         const familyId1 = familiesUploaded.filter((item) =>
            item.name === compatibilityConstraints[index].family1)[0].id;
         const familyId2 = familiesUploaded.filter((item) =>
            item.name === compatibilityConstraints[index].family2)[0].id;
         const element = {
            componentFamilyId1: familyId1,
            componentFamilyId2: familyId2,
         }
         fetch("http://localhost:8080/compatibilityConstraint", {
            method: "POST",
            body: JSON.stringify(element),
            headers: { "Content-Type": "application/json", token: token }
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);
            compatibilityConstraintsUploaded.push(json);
            recursiveInsertCompatibilityConstraints(index + 1).then(() => {
               resolve();
            });
         });
      }
   })
}

const populateComponentTypesTable = () => {
   let tableTemplate = '<tr><th scope="row">%ID%</th><td>%NAME%</td></tr>';
   const entryPoint = document.getElementById("componentTypeBody");
   let html = "";
   typesUploaded.forEach((type) => {
      let row = tableTemplate.replace("%ID%", type.id).replace("%NAME%", type.name);
      html += row;
   })
   entryPoint.innerHTML = html;
}

const populateComponentFamiliesTable = () => {
   let tableTemplate = '<tr><th scope="row">%ID%</th><td>%NAME%</td><td>%TYPENAME%</td></tr>';
   const entryPoint = document.getElementById("componentFamiliesBody");
   let html = "";
   familiesUploaded.forEach((family) => {
      let row = tableTemplate.replace("%ID%", family.id).replace("%NAME%", family.name)
         .replace("%TYPENAME%", family.type.name);
      html += row;
   })
   entryPoint.innerHTML = html;
}

const populateComponentsTable = () => {
   let tableTemplate = '<tr><th scope="row">%ID%</th><td>%NAME%</td><td>%FAMILYNAME%</td><td>%TYPENAME%</td></tr>';
   const entryPoint = document.getElementById("componentsBody");
   let html = "";
   componentsUploaded.forEach((component) => {
      let row = tableTemplate.replace("%ID%", component.id).replace("%NAME%", component.name)
         .replace("%FAMILYNAME%", component.componentFamily.name).replace("%TYPENAME%", component.componentFamily.type.name);;
      html += row;
   })
   entryPoint.innerHTML = html;
}

const populateCompatibilityConstraintsTable = () => {
   let tableTemplate = '<tr><th scope="row">%ID%</th><td>%FAMILY1NAME%</td><td>%FAMILY2NAME%</td></tr>';
   const entryPoint = document.getElementById("compatibilityConstraintsBody");
   let html = "";
   compatibilityConstraintsUploaded.forEach((compatibilityConstraint) => {
      let row = tableTemplate.replace("%ID%", compatibilityConstraint.id)
         .replace("%FAMILY1NAME%", compatibilityConstraint.componentFamily1.name)
         .replace("%FAMILY2NAME%", compatibilityConstraint.componentFamily2.name);;
      html += row;
   })
   entryPoint.innerHTML = html;
}

const insertAll = () => {
   login().then(() => {
      deleteAll().then(() => {
         recursiveInsertTypes().then(() => {
            console.log(JSON.stringify(typesUploaded));
            populateComponentTypesTable();
            recursiveInsertFamilies().then(() => {
               populateComponentFamiliesTable();
               console.log(JSON.stringify(familiesUploaded));
               recursiveInsertCompatibilityConstraints().then(() => {
                  populateCompatibilityConstraintsTable();
                  console.log(JSON.stringify(compatibilityConstraintsUploaded));
                  recursiveInsertComponents().then(() => {
                     populateComponentsTable();
                     console.log(JSON.stringify(componentsUploaded));
                  })
               })
            })
         })
      });
   })
};

export { insertAll };

