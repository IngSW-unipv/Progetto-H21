package it.skinjobs.api;

import it.skinjobs.model.ComponentType;
import it.skinjobs.dto.ComponentTypeDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jessica Vecchia
 *
 * The REST controller transforms all the methods into web services and the classes into JSON object. The methods define
 * calls to URLs via HTTP request(POST, GET, PUT, DELETE...)
 */
@RestController
public class ComponentTypeAPI extends DeleteAllAPI<ComponentType, ComponentTypeDTO, Integer> {
   @Autowired
   private ComponentTypes componentTypes;

   @Autowired
   private ComponentFamilyAPI componentFamilyAPI;

   /**
     *
     * @return ResponseBody
     *
     * This API returns all the component types.
     */
   @CrossOrigin(origins = "*")
   @GetMapping("/componentTypes")
   public @ResponseBody Iterable<ComponentType> getAll() {
      return componentTypes.findAllSorted();
   }

   /**
     *
     * @param index
     * @return ResponseBody
     *
     * This API returns a component type according to its id.
     */
   @CrossOrigin(origins = "*")
   @GetMapping("/componentTypes/{index}")
   public ResponseEntity<ComponentType> getById(@PathVariable Integer index) {
      Optional<ComponentType> result = this.componentTypes.findById(index);
      return new ResponseEntity<>(result.get(), HttpStatus.OK);
   }

   /**
     *
     * @param headers
     * @param componentTypeDTO
     * @return ResponseEntity
     *
     * This API allows the admin to add a new component type.
     */
   @CrossOrigin(origins = "*")
   @PostMapping("/componentType")
   public ResponseEntity<ComponentType> newElement(@RequestHeader Map<String, String> headers,
         @RequestBody ComponentTypeDTO componentTypeDTO) {
      return super.sessionOperation(headers, componentTypeDTO, new Callable<>() {
         @Override
         public ResponseEntity<ComponentType> call(@RequestBody ComponentTypeDTO componentTypeDTO) {
            ComponentType componentType = new ComponentType();
            componentType.setName(componentTypeDTO.getName());
            componentType.setSortOrder(componentTypeDTO.getSortOrder());
            return new ResponseEntity<>(componentTypes.save(componentType), HttpStatus.OK);
         }
      });

   }

   /**
     *
     * @param headers
     * @param componentTypeDTO
     * @param index
     * @return ResponseEntity
     *
     * This API allow the admin to modify a component type
     */
   @CrossOrigin(origins = "*")
   @PutMapping("/componentType/{index}")
   public ResponseEntity<ComponentType> updateElement(@RequestHeader Map<String, String> headers,
         @RequestBody ComponentTypeDTO componentTypeDTO, @PathVariable Integer index) {
      return super.sessionOperation(headers, componentTypeDTO, new Callable<>() {
         @Override
         public ResponseEntity<ComponentType> call(ComponentTypeDTO componentTypeDTO) {
            ComponentType result = componentTypes.findById(index).map(componentType -> {
                componentType.setName(componentTypeDTO.getName());
                componentType.setSortOrder(componentTypeDTO.getSortOrder());
                return componentTypes.save(componentType);
            }).orElseGet(() -> null);
                return new ResponseEntity<>(result, HttpStatus.OK);
          }
      });

   }

   /**
     *
     * @param headers
     * @param typeId
     * @return boolean
     *
     * This API allows the admin to delete a component type.
     */
   @CrossOrigin(origins = "*")
   @DeleteMapping("/componentType/{typeId}")
   public ResponseEntity<Boolean> deleteElement(@RequestHeader Map<String, String> headers, @PathVariable Integer typeId) {
      return super.sessionDeleteOperation(headers, typeId);
   }

   /**
     *
     * @param headers
     * @return boolean
     *
     * This API deletes all the component types and consequentially all the related entities. Even if in this version this is not used
     * by client, this Api can be useful for later extensions.
     */
   @CrossOrigin(origins = "*")
   @DeleteMapping("/deleteAll")
   public ResponseEntity<Boolean> deleteAll(@RequestHeader Map<String, String> headers) {
       return super.sessionDeleteAllOperation(headers, new Callable<>(){
         @Override
         public ResponseEntity<Boolean> call(ComponentTypeDTO componentTypeDTO) {
            Iterable<ComponentType> componentTypeList = componentTypes.findAll();
            for(ComponentType componentType: componentTypeList){
               deleteEntity(componentType.getId());
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
         }
      });    
   }

   public Boolean deleteEntity(Integer typeId) {
      if (componentTypes.findById(typeId).isPresent()) {
         componentFamilyAPI.deleteCascade(typeId);
         componentTypes.deleteById(typeId);
         return true;
      } else {
         return false;
      }
   }



}
