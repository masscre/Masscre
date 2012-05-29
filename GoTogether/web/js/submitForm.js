function submitLoginForm()
            {
                oFormObject = document.forms['login'];
                if (oFormObject.elements["password"].value != "") {
                    oFormObject.elements["password"].value = hex_sha256(oFormObject.elements["password"]);
                }
                document.forms["login"].submit();                
            }
            
function submitRegisterForm()
            {
                oFormObject = document.forms['register'];
                if (oFormObject.elements["password"].value != "") {
                    oFormObject.elements["password"].value = hex_sha256(oFormObject.elements["password"]);
                }
                if (oFormObject.elements["passwordcheck"].value != "") {
                    oFormObject.elements["passwordcheck"].value = hex_sha256(oFormObject.elements["passwordcheck"]);
                }
                document.forms["register"].submit();                
            }            