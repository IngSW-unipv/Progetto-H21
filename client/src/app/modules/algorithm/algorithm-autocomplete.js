
/**
 * @author Jessica Vecchia
 */
const randomCreator = (compatibileComponents, setup) => {
  // svolge la funzione dell'add component ma random
  const innerCycle = (filteredComponents, setup, resolve) => {
    const randomIndex = Math.floor(Math.random() * filteredComponents.length);
    const choosenComponent = filteredComponents[randomIndex];
    setup.push(choosenComponent);
    fetch("http://localhost:8080/compatibilityContraints/getByComponentId/" + choosenComponent.id).then((result) => {
        return result.json();
//passa i componenti compatibili in un json, questi componenti verranno selezionati random
//per andare a fare parte del setup
    }).then((json) => {
        randomCreator(json, setup).then((setup) => resolve(setup));
    });
  }
  //essenzialmente il promise svolge sia il mio getComponents e il mio initSetup
  return new Promise((resolve, reject) => {
    if (setup === undefined) {
        setup = []; //se non esiste un setup lo crea
        //
        fetch("http://localhost:8080/componentTypes").then((result) => {
          return result.json();
        }).then((json) => {
          fetch("http://localhost:8080/components").then((result) => {
              return result.json();
          }).then((json) => {
              let filteredComponents = json.filter((element) => element.componentFamily.type.id === currentType.id);
              //entra nell'inner join passando all'interno i componenti per tipo il setup e il resolver
              innerCycle(filteredComponents, setup, resolve);
          });
        });
    } else {
        if (compatibileComponents.length === 0) {
          //se non ci sono componenti compatibili la promise restituisce il setup
          resolve(setup);
        } else {
          //altrimenti esegue l'innercyrcle massando come parametri, i componenti compatibili, il setup e il resolve
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

export { create };
