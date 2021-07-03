(() => {

   let token = "";

   window.onload = () => {
      document.getElementById("buttonLogin").addEventListener("click", () => {
         const name = document.getElementById("nameInput").value;
         const password = document.getElementById("passwordInput").value;
         fetch("http://localhost:8080/login", {
            method: "POST",                  
            body: JSON.stringify({ name: name, password: password}),
            headers: {"Content-Type": "application/json"}
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);   
            token = json["token"];    
            document.getElementById("result").innerText = "Login effettuata: " + token;      
         }).catch((error) => {
            document.getElementById("result").innerText = JSON.stringify(error);
         })
      });
   
      document.getElementById("buttonSend").addEventListener("click", () => {
         let image = document.getElementById("upload").files[0];
         let formData = new FormData();
         formData.append("file", image);
         fetch("http://localhost:8080/uploadFile", {
            method: "POST",                  
            body: formData,
            headers: {token: token}
         }).then((result) => {
            return result.json()
         }).then((json) => {
            console.log(json);
            console.log(JSON.stringify(json));
            document.getElementById("result").innerText = JSON.stringify(json);
            document.getElementById("imgResult").setAttribute("src", json["fileDownloadUri"]);
         }).catch((error) => {
            document.getElementById("result").innerText = JSON.stringify(error);
         })

      })
   }
})();


   
   
