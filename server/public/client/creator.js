// NOT TESTED
const randomCreator = (compatibileComponents, setup) => {
   const innerCycle = (filteredComponents, setup, resolve) => {
      const randomIndex = Math.floor(Math.random() * filteredComponents.length);
      const choosenComponent = filteredComponents[randomIndex];
      setup.push(choosenComponent);
      fetch("http://localhost:8080/compatibilityContraints/getByComponentId/" + choosenComponent.id).then((result) => {
         return result.json();
      }).then((json) => {
         randomCreator(json, setup).then((setup) => resolve(setup));
      });
   }
   return new Promise((resolve, reject) => {
      if (setup === undefined) {
         setup = [];
         fetch("http://localhost:8080/componentTypes").then((result) => {
            return result.json();
         }).then((json) => {
            const sorted = json.sort((first, second) => first.sortOrder < second.sortOrder);
            const currentType = sorted[0];
            fetch("http://localhost:8080/components").then((result) => {
               return result.json();
            }).then((json) => {
               let filteredComponents = json.filter((element) => element.componentFamily.type.id === currentType.id);
               innerCycle(filteredComponents, setup, resolve);
            });
         });
      } else {
         if (compatibileComponents.length === 0) {
            resolve(setup);
         } else {
            innerCycle(compatibileComponents, setup, resolve);
         }
      }
   });
}

const populateSetup = (setup) => {
   let tableTemplate = '<tr><th scope="row">%ID%</th><td>%NAME%</td><td>%TYPE%</td><td>%POWER%</td><td>%PRICE%</td></tr>';
   const entryPoint = document.getElementById("setupBody");
   let html = "";
   setup.forEach((component) => {
      let row = tableTemplate.replace("%ID%", component.id)         
         .replace("%NAME%", component.name)
         .replace("%TYPE%", component.componentFamily.type.name)
         .replace("%POWER%", component.powerSupply)
         .replace("%PRICE%", component.price);
      html += row;
   })
   let row = tableTemplate.replace("%ID%", "total")         
         .replace("%NAME%", "")
         .replace("%TYPE%", "")
         .replace("%POWER%", "W " + setup.reduce((acc, current) => acc + current.powerSupply, 0))
         .replace("%PRICE%", "€ " + setup.reduce((acc, current) => acc + current.price, 0));
      html += row;
   entryPoint.innerHTML = html;
}

const create = () => {
   randomCreator().then(populateSetup);
}

export { create};