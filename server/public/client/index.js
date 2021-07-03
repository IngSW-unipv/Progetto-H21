import { insertAll } from './inserter.js';
import { create } from './creator.js';

(() => {


   const navigation = () => {
      document.getElementById("createPage").classList.add("hide");
      document.getElementById("insertLink").addEventListener("click", () => {
         document.getElementById("createPage").classList.add("hide");
         document.getElementById("insertPage").classList.remove("hide");
      });
      document.getElementById("createLink").addEventListener("click", () => {
         document.getElementById("insertPage").classList.add("hide");
         document.getElementById("createPage").classList.remove("hide");
      });
         
   }   

   window.onload = () => {      
      navigation();
      document.getElementById("buttonStart").addEventListener("click", () => {
         insertAll();
      });
      document.getElementById("buttonCreate").addEventListener("click", () => {
         create();
      });
   }
})();





