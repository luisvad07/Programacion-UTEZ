<?php
$Ser= "LocalHost";
$user= "root";
$pass = "alejandro";
$db = "sege";

//Variable a usar para conexion

$conex = null;
$conex= mysqli_connect($Ser,$user,$pass,$db);


//Esta es la consulta a los municipios

$csltTest= "SELECT * FROM  encuestas ORDER BY id_Encuestas";
$excTest = mysqli_query($conex ,$csltTest) or die(mysqli_error($conex));

?>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

      <script src="js/query-3.6.1.js"></script>

      <!-- SweetAlert -->
      <script src="js/sweetalert2@11.js"></script>

      <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>SEGE!</title>
  </head>
  <body>

    <div class="px-2">
      <!-- First part to answer the test -->
        <form action="model/DaoPeople.php" method="post">
            <section id="fisrtPart">
                <div class="container-fluid">
                    <div class="card px-5 py-2 mt-1 shadow">

                        <div class="row  border-bottom mb-2 ">
                            <div class="col-2 d-flex justify-content-center "><img src="UTEZ.svg" alt="Logo UTEZ" width="67%"></div>
                            <div class="col-8">

                                <h1 class="fs-2 mt-1 text-center m-0 border-bottom border-success "><b class="text-success ">SEGE</b></h1>
                                <p class="m-0 text-center fs-5 fst-italic ">Sistema Estadístico de Gestión de Encuesta de Calidad de Software Escolar</p>

                            </div>
                            <div class="col-2 d-flex justify-content-center"><img src="DATID.svg" alt="Logo DATID" width="100%"></div>
                        </div>

                        <div class="row">
                            <div class="col-10">
                                <div class="row mb-1 ">
                                    <label class="fs-6 text-success fw-bold col-4">Selecciona la encuesta:<sup class="text-danger">*</sup></label>
                                    <div class="col-7">
                                        <select class="form-select   form-select-sm" id="nameTest" name="test" required>
                                            <?php foreach ($excTest as $test) :   ?>

                                                <option id="nombre" value="<?php echo $test['id_Encuestas']; ?>">

                                                    <?php echo $test['name']; ?>

                                                </option>

                                            <?php endforeach ?>
                                        </select>
                                    </div>
                                </div>

                                <div class="row ">
                                    <label class="fs-6 my-2 text-success fw-bold col-4 ">Ingrese el nombre de la escuela:<sup class="text-danger">*</sup></label>
                                    <div class="col-7">
                                        <input type="text" class="form-control form-control-sm name" name="name_school" >
                                    </div>

                                </div>

                                <div class="row ">
                                    <label class="fs-6 my-2  text-success fw-bold col-4">Ingrese su nombre <sup class="text-danger">*</sup></label>
                                    <div class="col-7">
                                        <input type="text" class="form-control form-control-sm name" name="name_client" >

                                    </div>
                                </div>
                            </div>

                            <div class="col-2">
                                <h4 class="display-6 mb-0">Promedio:</h4>
                                <p id="promedio" class="display-6 fw-bold text-success text-center mb-0">0%</p>
                                <input type="hidden" name="average" class="average" >
                            </div>
                        </div>


                        <div class="container-fluid  px-5 general">
                            <div class="row">

                                <div class="col-12 mb-2">
                                    <div class="row">
                                        <div class="col-2"></div>
                                        <div class="col-2 fw-bold text-center">Mala</div>
                                        <div class="col-2 fw-bold text-center">Regular</div>
                                        <div class="col-2 fw-bold text-center">Bueno</div>
                                        <div class="col-2 fw-bold text-center">Muy buena</div>
                                        <div class="col-2 fw-bold text-center">Excelente</div>
                                    </div>
                                </div>

                                <div class="test">
                                    <div class="col-12">
                                        <div class="row ">
                                            <div class="col-2 fw-bold pt-2">Funcionalidad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="funcional bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="funcional regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="funcional fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="funcional veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="funcional exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Confiabilidad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="confiabilidad bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="confiabilidad regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="confiabilidad fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="confiabilidad veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="confiabilidad exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Usabilidad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="usabilidad bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="usabilidad regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="usabilidad fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="usabilidad veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="usabilidad exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Rendimiento</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="rendimiento bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="rendimiento regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="rendimiento fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="rendimiento veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="rendimiento exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Mantenimiento</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="mantenimiento bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="mantenimiento regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="mantenimiento fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="mantenimiento veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="mantenimiento exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Portabilidad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="portabilidad bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="portabilidad regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="portabilidad fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="portabilidad veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="portabilidad exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Seguridad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="seguridad bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="seguridad regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="seguridad fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="seguridad veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="seguridad exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-2 fw-bold pt-2">Compatibilidad</div>
                                            <div class="col-2 text-center"><a  href="#bad"><img  class="compatibilidad bad" src = "emojis/badBlack.svg" alt="MaloEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#regular"><img class="compatibilidad regular" src = "emojis/regularBlack.svg" alt="RegularEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#fine"><img class="compatibilidad fine" src = "emojis/buenoBlack.svg" alt="BuenoEmoji" width="50px" height="50px" /></a></div>
                                            <div class="col-2 text-center "><a  href="#veryFine"><img  class="compatibilidad veryFine" src = "emojis/muyBuenoBlack.svg" alt="MuyBuenoEmoji" width="50" height="50" /></a></div>
                                            <div class="col-2 text-center "><a  href="#exelent"><img class="compatibilidad exelent" src = "emojis/exelenteBlack.svg" alt="ExcelenteEmoji" width="50" height="50" /></a></div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div>
                            <input type="hidden" name="funcionality" class="funcionality" >
                            <input type="hidden" name="confiabilidad" class="conf" >
                            <input type="hidden" name="usability" class="usability" >
                            <input type="hidden" name="efficiency" class="efficiency" >
                            <input type="hidden" name="maintenance" class="maintenance" >
                            <input type="hidden" name="portability" class="portability" >
                            <input type="hidden" name="safety" class="safety" >
                            <input type="hidden" name="compatibility" class="compatibility" >
                        </div>


                        <div class="d-flex justify-content-between pt-2 border-top ">
                            <button class="btn btn-outline-info px-5" data-bs-toggle="modal" href="#exampleModalToggle" role="button" TYPE="button">Ver resultado general de la encuesta</button>
                            <button class="btn btn-outline-success px-5" id="save" name="save" type="button">Guardar</button>
                        </div>

                    </div>
                </div>
            </section>
        </form>

    </div>

<!-----------------------------------------MODAL------------------------------------------------------------------>
   <div class="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalToggleLabel">SEGE</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body ">
                <div class="alert alert-success" role="alert">
                    <div class=" mb-1 ">
                        <label class="fs-5 text-success fw-bold mb-2">Selecciona la encuesta:<sup class="text-danger">*</sup></label>
                        <select class="form-select form-select-lg  " id="check">
                            <?php foreach ($excTest as $test) :   ?>

                                <option id="nombre" value="<?php echo $test['id_Encuestas']; ?>">

                                    <?php echo $test['name']; ?>

                                </option>

                            <?php endforeach ?>
                        </select>
                    </div>
                </div>

               <div class="container">
                   <div class="fs-3 my-3">
                       <b >Encuestas realizadas:  </b> <span id="a" class="text-decoration-underline "> </span>

                   </div>

                   <div class="fs-3">
                       <b>Promedio general: </b> <samp id="b" class="text-decoration-underline"></samp>  %

                   </div>
               </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
    </div>

    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <script src="js/function.js"> </script>


    <script>
        // Inicio de funcion de ajax para enviar valor de select municipio a php
        $(document).ready(function(){
            recargarLista(); //Vamos a recargar el select a un div

            $('#check').change(function(){ //activar la funcion cada que se hga un cambio en la lista
                recargarLista();
            });

        })


        function recargarLista(){ //pasamos datos a php
            $.ajax({
                type:"POST",
                url:"model/DaoPeople.php", //lo mandamos al archivo
                data: {"idTest" : $('#check').val() },
                success:function(r){
                    var result = r.split(' ');

                    console.log(result)
                    console.log(r)
                    $('#a').html(result[0]); //Si tiene algo lo va a mandar al div
                    $('#b').html(result[1]); //Si tiene algo lo va a mandar al div

                }
            });
        }
    </script>



  </body>
</html>