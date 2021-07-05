package it.skinjobs.api;

import it.skinjobs.model.CompatibilityConstraint;
import it.skinjobs.model.Component;
import it.skinjobs.model.ComponentFamily;
import it.skinjobs.dto.CompatibilityConstraintDTO;
import it.skinjobs.repository.CompatibilityConstraints;
import it.skinjobs.repository.ComponentFamilies;
import it.skinjobs.repository.Components;
import it.skinjobs.utils.Callable;

import java.util.ArrayList;
import java.util.List;
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
public class CompatibilityConstraintsAPI extends BaseAPI<CompatibilityConstraint, CompatibilityConstraintDTO, Integer> {
   /**
     * At runtime a concrete class implementing the injected interface will be created(with the same name of the interface)
     * and through the application context the class will be instantiated via a factory pattern
     */
   @Autowired
   private CompatibilityConstraints compatibilityConstraints;

   @Autowired
   private ComponentFamilies componentFamilies;

   @Autowired
   private Components components;

   /**
     *
     * @param componentId
     * @return ResponseEntity
     *
     * This API returns all the components that are compatible with a given component.
     */
   @CrossOrigin(origins = "*")    
   @GetMapping("/compatibilityConstraints/getByComponentId/{componentId}")
   public @ResponseBody List<Component> getCompatibleComponentsByComponentId(@PathVariable Integer componentId) {   //@ResponseBody HttpEntity<List<Component>>
      Optional<Component> optional = this.components.findById(componentId);
      if (optional.isPresent()) {
         Component component = optional.get();
         ComponentFamily componentFamily = component.getComponentFamily();
         List<CompatibilityConstraint> constraints = this.compatibilityConstraints
               .findByFamilyId(componentFamily.getId());
         List<ComponentFamily> compatibleFamilies = new ArrayList<>();
         for (CompatibilityConstraint constraint : constraints) {
            compatibleFamilies.add(constraint.getComponentFamily2());
         }
         List<Component> result = new ArrayList<>();
         for (ComponentFamily compatibleFamily : compatibleFamilies) {
            result.addAll(components.findComponentsByFamilyId(compatibleFamily.getId()));            
         }
         return result;  
      } else {
         return new ArrayList<>();  
      }
   }

   /**
     *
     * @return ResponseBody
     *
     * This API returns all the compatibility constraints in the database.
     */
   @CrossOrigin(origins = "*")
   @GetMapping("/compatibilityConstraints")
   public @ResponseBody Iterable<CompatibilityConstraint> getAll() {
      return this.compatibilityConstraints.findAll();
   }

   /**
     *
     * @param index
     * @return ResponseBody
     *
     * This API returns a specific compatibility constraint row according to its id.
     */
   @CrossOrigin(origins = "*")
   @GetMapping("/compatibilityConstraints/{index}")
   public ResponseEntity<CompatibilityConstraint> getById(@PathVariable Integer index) {
      Optional<CompatibilityConstraint> result = this.compatibilityConstraints.findById(index);
         return new ResponseEntity<>(result.get(), HttpStatus.OK);
   }

   /**
     *
     * @param headers
     * @param compatibilityConstraintDTO
     * @return ResponseEntity
     *
     * This API allows the admin to add a compatibility constraint to the database.
     */
   @CrossOrigin(origins = "*")
   @PostMapping("/compatibilityConstraint")
   public ResponseEntity<CompatibilityConstraint> newElement(@RequestHeader Map<String, String> headers,
         @RequestBody CompatibilityConstraintDTO compatibilityConstraintDTO) {
      return super.sessionOperation(headers, compatibilityConstraintDTO, new Callable<>() {
         @Override
         public ResponseEntity<CompatibilityConstraint> call(CompatibilityConstraintDTO compatibilityConstraintDTO) {
            CompatibilityConstraint compatibilityConstraint = new CompatibilityConstraint();
            Optional<ComponentFamily> optionalFamily1 = componentFamilies
                  .findById(compatibilityConstraintDTO.getComponentFamilyId1());
            Optional<ComponentFamily> optionalFamily2 = componentFamilies
                  .findById(compatibilityConstraintDTO.getComponentFamilyId2());
            if (optionalFamily1.isPresent() && optionalFamily2.isPresent()) {
               compatibilityConstraint.setComponentFamily1(optionalFamily1.get());
               compatibilityConstraint.setComponentFamily2(optionalFamily2.get());
               return new ResponseEntity<>(compatibilityConstraints.save(compatibilityConstraint), HttpStatus.OK);
            } else {
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
         }
      });
   }

   /**
     *
     * @param headers
     * @param compatibilityConstraintDTO
     * @param index
     * @return ResponseEntity
     *
     * This API allows the admin to modify a compatibility constraint to the database.
     */
   @CrossOrigin(origins = "*")
   @PutMapping("/compatibilityConstraint/{index}")
   public ResponseEntity<CompatibilityConstraint> updateElement(@RequestHeader Map<String, String> headers,
         @RequestBody CompatibilityConstraintDTO compatibilityConstraintDTO, final @PathVariable Integer index) {
      return super.sessionOperation(headers, compatibilityConstraintDTO, new Callable<>() {
         @Override
         public ResponseEntity<CompatibilityConstraint> call(CompatibilityConstraintDTO compatibilityConstraintDTO) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
         }
      });

   }

   /**
     *
     * @param headers
     * @param index
     * @return boolean
     *
     * This API allows the admin to delete a compatibility constraint to the database.
     */
   @CrossOrigin(origins = "*")
   @DeleteMapping("/compatibilityConstraint/{index}")
   public ResponseEntity<Boolean> deleteElement(@RequestHeader Map<String, String> headers, @PathVariable Integer index) {
     return super.sessionDeleteOperation(headers, index);
   }

   /**
     *
     * @param familyId
     *
     * This method is necessary to avoid database inconsistency due to the deletion of a family id.
     */
   public void deleteCascade(Integer familyId) {
      Iterable<CompatibilityConstraint> compatibilityConstraintList = this.compatibilityConstraints.findAll();
      for (CompatibilityConstraint compatibilityConstraint : compatibilityConstraintList) {
         if (compatibilityConstraint.getComponentFamily1().getId().equals(familyId)
               || compatibilityConstraint.getComponentFamily2().getId().equals(familyId)) {
            compatibilityConstraints.deleteById(compatibilityConstraint.getId());
         }
      }
   }

   /**
     *
     * @param index
     * @return boolean
     *
     * This method deletes the database entity.
     */
   public Boolean deleteEntity(Integer index) {
      if (compatibilityConstraints.findById(index).isPresent()) {
         compatibilityConstraints.deleteById(index);
         return true;
      } else {
         return false;
      }
   }

}
