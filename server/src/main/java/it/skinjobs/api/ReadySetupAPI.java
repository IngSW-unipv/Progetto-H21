package it.skinjobs.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
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

import it.skinjobs.model.Component;
import it.skinjobs.model.ReadySetup;
import it.skinjobs.dto.ReadySetupDTO;
import it.skinjobs.repository.Components;
import it.skinjobs.repository.ReadySetups;
import it.skinjobs.utils.Callable;

/**
 *
 * @author Andrei Blindu
 * @author Filippo Maria Rognoni
 * @author Jessica Vecchia
 *
 * The REST controller transforms all the methods into web services and
 * the classes into JSON object. The methods define calls to URLs via
 * HTTP request(POST, GET, PUT, DELETE...)
 */
@RestController
public class ReadySetupAPI extends BaseAPI<ReadySetup, ReadySetupDTO, Integer> {

   @Autowired
   private ReadySetups readySetups;

   @Autowired
   private Components components;

   /**
    *
    * @return ResponseBody
    *
    *         This API returns all the ready setup.
    */
   @CrossOrigin(origins = "*")
   @GetMapping("/readySetups")
   public @ResponseBody Iterable<ReadySetup> getAll() {
      return readySetups.findAll();
   }

   /**
    *
    * @param index
    * @return ResponseBody
    *
    *         This API returns a ready setup according to its id.
    */
   @CrossOrigin(origins = "*")
   @GetMapping("/readySetups/{index}")
   public ResponseEntity<ReadySetup> getById(@PathVariable Integer index) {
      Optional<ReadySetup> result = this.readySetups.findById(index);
      return new ResponseEntity<>(result.get(), HttpStatus.OK);
   }

   /**
    *
    * @param headers
    * @param readySetupDTO
    * @return ResponseEntity
    *
    *         This API allows the admin to add a new ready setup.
    */
   @CrossOrigin(origins = "*")
   @PostMapping("/readySetup")
   public ResponseEntity<ReadySetup> newElement(@RequestHeader Map<String, String> headers,
         @RequestBody ReadySetupDTO readySetupDTO) {
      return super.sessionOperation(headers, readySetupDTO, new Callable<>() {
         @Override
         public ResponseEntity<ReadySetup> call(ReadySetupDTO readySetupDTO) {
            ReadySetup readySetup = new ReadySetup();
            readySetup.setName(readySetupDTO.getName());
            Set<Component> componentList = new HashSet<>();

            for (Integer componentId : readySetupDTO.getComponentList()) {
               Optional<Component> component = components.findById(componentId);
               if (component.isPresent()) {
                  componentList.add(component.get());
               }
            }

            readySetup.setComponentList(componentList);
            readySetup.setUsage(readySetupDTO.getUsage());
            readySetup.setImagePath(readySetupDTO.getImagePath());
            readySetup.setTotalPrice(readySetupDTO.getTotalPrice());
            return new ResponseEntity<>(readySetups.save(readySetup), HttpStatus.OK);

         }

      });
   }

   /**
    *
    * @param headers
    * @param readySetupDTO
    * @param index
    * @return ResponseEntity
    *
    *         This API allows the admin to modify a ready setup.
    */
   @CrossOrigin(origins = "*")
   @PutMapping("/readySetup/{index}")
   public ResponseEntity<ReadySetup> updateElement(@RequestHeader Map<String, String> headers,
         @RequestBody ReadySetupDTO readySetupDTO, final @PathVariable Integer index) {
      return super.sessionOperation(headers, readySetupDTO, new Callable<>() {
         @Override
         public ResponseEntity<ReadySetup> call(ReadySetupDTO readySetupDTO) {
            ReadySetup result = readySetups.findById(index).map(readySetup -> {
               readySetup.setName(readySetupDTO.getName());
               Set<Component> componentList = new HashSet<>();

               for (Integer componentId : readySetupDTO.getComponentList()) {
                  Optional<Component> component = components.findById(componentId);
                  if (component.isPresent()) {
                     componentList.add(component.get());
                  }
               }
               readySetup.setComponentList(componentList);
               readySetup.setUsage(readySetupDTO.getUsage()); // ** */
               readySetup.setImagePath(readySetupDTO.getImagePath()); // ** */
               return readySetups.save(readySetup);

            }).orElseGet(() -> null);
            if (result != null) {
               return new ResponseEntity<>(result, HttpStatus.OK);
            }

            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
         }
      });

   }

   /**
    *
    * @param headers
    * @param index
    * @return ResponseEntity
    *
    *         This API allows the admin to delete a ready setup.
    */
   @CrossOrigin(origins = "*")
   @DeleteMapping("/readySetup/{index}")
   public ResponseEntity<Boolean> deleteElement(@RequestHeader Map<String, String> headers,
         @PathVariable Integer index) {
      return super.sessionDeleteOperation(headers, index);
   }

   /**
    * @author Andrei Blindu
    * @author Jessica Vecchia
    * @param componentId
    */
   @CrossOrigin(origins = "*")
   public void deleteCascade(Integer componentId) {
      Iterable<ReadySetup> readySetupList = this.readySetups.findAll();
      for (ReadySetup readySetUp : readySetupList) {
         for (Component component : readySetUp.getComponentList()) {
            if (component.getId() == componentId) {
               deleteEntity(readySetUp.getId());
               break;
            }
         }
      }
   }

   /**
    * @return Boolean
    */
   public Boolean deleteEntity(Integer index) {
      if (readySetups.findById(index).isPresent()) {
         readySetups.deleteById(index);
         return true;
      } else {
         return false;
      }
   }

}
