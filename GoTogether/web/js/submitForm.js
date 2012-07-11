function submitLoginForm()
            {
                oFormObject = document.forms['login'];  
                password = oFormObject.elements["password"].value;
                oFormObject.elements["password"].value = hex_sha256(password);               
                document.forms["login"].submit();                
            }
            
function submitRegisterForm()
            {
                oFormObject = document.forms['register'];
                password = oFormObject.elements["password"].value;
                passwordcheck = oFormObject.elements["passwordcheck"].value;
                oFormObject.elements["password"].value = hex_sha256(password);                
                oFormObject.elements["passwordcheck"].value = hex_sha256(passwordcheck);                
                document.forms["register"].submit();                
            }            