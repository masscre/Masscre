function submitLoginForm()
            {
                oFormObject = document.forms['login'];
                oFormObject.elements["password"].value = hex_sha256(oFormObject.elements["password"]);
                document.forms["login"].submit();                
            }
            
function submitRegisterForm()
            {
                oFormObject = document.forms['register'];
                oFormObject.elements["password"].value = hex_sha256(oFormObject.elements["password"]);
                oFormObject.elements["passwordcheck"].value = hex_sha256(oFormObject.elements["passwordcheck"]);
                document.forms["register"].submit();                
            }            