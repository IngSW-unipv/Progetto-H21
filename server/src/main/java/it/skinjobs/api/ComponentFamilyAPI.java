package it.skinjobs.api;

import it.skinjobs.model.ComponentFamily;
import it.skinjobs.model.ComponentType;
import it.skinjobs.dto.ComponentFamilyDTO;
import it.skinjobs.repository.ComponentFamilies;
import it.skinjobs.repository.ComponentTypes;
import it.skinjobs.utils.Callable;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jessica Vecchia
 *
 * The REST controller transforms all the methods into web services and the classes into JSON object. The methods define
 * calls to URLs via HTTP request(POST, GET, PUT, DELETE...)
 */
@RestController
public class ComponentFamilyAPI extends BaseAPI<ComponentFamily, ComponentFamilyDTO, Integer>{
   @Autowired
   private ComponentFamilies componentFamilies;

   @Autowired
   private ComponentTypes componentTypes;

   @Autowired
   private CompatibilityConstraintsAPI compatibilityConstraintsAPI;

   @Autowired
   private ComponentAPI componentAPI;

   /**
     *
     * @return ResponseBody
     *
     * This API returns all the component families.
     */
   @CrossOrigin(origins = "*")   
    @GetMapping("/componentFamilies")
    public @ResponseBody Iterable<ComponentFamily> getAll() {
        return componentFamilies.findAll();
    }

    /**
     *
     * @param index
     * @return ResponseBody
     *
     * This API returns a component family according to its id.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/componentFamilies/{index}")
    public ResponseEntity<ComponentFamily> getById(@PathVariable Integer index) {
       Optional<ComponentFamily> result = this.componentFamilies.findById(index);
       return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }
    
    /**
     *
     * @param headers
     * @param componentFamilyDTO
     * @return ResponseEntity
     *
     * This API allows the admin to add a component family.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/componentFamily")
    public ResponseEntity<ComponentFamily> newElement(@RequestHeader Map<String,String> headers, @RequestBody ComponentFamilyDTO componentFamilyDTO) {
      return super.sessionOperation(headers, componentFamilyDTO, new Callable<>(){
         @Override
         public ResponseEntity<ComponentFamily> call(ComponentFamilyDTO componentFamilyDTO) {
            ComponentFamily componentFamily = new ComponentFamily();
            componentFamily.setName(componentFamilyDTO.getName());
            Optional<ComponentType> optionalType = componentTypes.findById(componentFamilyDTO.getTypeId());
            if (optionalType.isPresent()) {
               componentFamily.setType(optionalType.get());
               return new ResponseEntity<>(componentFamilies.save(componentFamily), HttpStatus.OK);         
            } else {
               return new ResponseEntity<>(null, HttpStatus.OK); 
            }  
         }
      });
     
    }

    /**
     *
     * @param headers
     * @param componentFamilyDTO
     * @param index
     * @return ResponseEntity
     *
     * This API allows the admin to modify a component family.
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/componentFamily/{index}")
    public ResponseEntity<ComponentFamily> updateElement(@RequestHeader Map<String, String> headers, @RequestBody ComponentFamilyDTO componentFamilyDTO, final @PathVariable Integer index) {
      return super.sessionOperation(headers, componentFamilyDTO, new Callable<>() {
            @Override
            public ResponseEntity<ComponentFamily> call(ComponentFamilyDTO componentFamilyDTO) {
               Optional<ComponentType> optionalComponentType = componentTypes.findById(componentFamilyDTO.getTypeId());
               if (optionalComponentType.isPresent()) {
                  return componentFamilies.findById(index).map(componentFamily -> {
                     componentFamily.setName(componentFamilyDTO.getName());
                     componentFamily.setType(optionalComponentType.get());
                     return new ResponseEntity<>(componentFamilies.save(componentFamily), HttpStatus.OK);
                  }).orElseGet(() -> null);
               } else {
                  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
               }
            }
         });
   
      }

   /**
     *
     * @param headers
     * @param index
     * @return boolean
     *
     * This API allows the admin to delete a component family.
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/componentFamily/{index}")
    public ResponseEntity<Boolean> deleteElement(@RequestHeader Map<String, String> headers, @PathVariable Integer index) {
       return super.sessionDeleteOperation(headers, index);
    }

    /**
     * @author Andrei Blindu
     * @author Jessica Vecchia
     * @param typeId
     * @return Boolean
     */
    public Boolean deleteCascade(Integer typeId) {
       Iterable <ComponentFamily> componentFamilyList = this.componentFamilies.findComponentFamilyByTypeId(typeId);
       for(ComponentFamily componentFamily: componentFamilyList){
          this.deleteEntity(componentFamily.getId());
       }
       return true;
    }

    /**
     * @author Andrei Blindu
     * @author Jessica Vecchia
     * @param  familyId
     * @return Boolean
     */
    public Boolean deleteEntity(Integer familyId) {
       if (componentFamilies.findById(familyId).isPresent()) {
         compatibilityConstraintsAPI.deleteCascade(familyId);
         componentAPI.deleteCascade(familyId);
         componentFamilies.deleteById(familyId);
         return true;
       } else {
          return false;
       }
    }
}
