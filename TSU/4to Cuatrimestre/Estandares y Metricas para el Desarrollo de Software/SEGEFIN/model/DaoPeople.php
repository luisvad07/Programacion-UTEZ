
<?php


$Ser= "LocalHost";
$user= "root";
$pass = "alejandro";
$db = "sege";

//Variable a usar para conexion

$conex = null;
$conex= mysqli_connect($Ser,$user,$pass,$db);


if (isset($_POST['save'])){
    require_once('alert.php');
    $id_test = $_POST['test'];
    $name_school = $_POST['name_school'];
    $name_client = $_POST['name_client'];
    $average = $_POST['average'];

    $funcionality = $_POST['funcionality'];
    $confiabilidad = $_POST['confiabilidad'];
    $usability = $_POST['usability'];
    $efficiency = $_POST['efficiency'];
    $maintenance = $_POST['maintenance'];
    $portability = $_POST['portability'];
    $safety = $_POST['safety'];
    $compatibility = $_POST['compatibility'];

   /*
    echo '<p>'.$id_test.'</p>' ;
     echo '<p>'.$name_school.'</p>' ;
    echo '<p>'.$name_client.'</p>' ;
    echo '<p>'.$average.'</p>' ;
   */

    $insertPersonTest = "INSERT INTO people (`client`, `school`, `funcionality`, `confiabilidad`, `usability`, `efficiency`, `maintenance`, `portability`, `safety`, `compatibility`, `average`, `encuestas_id_Encuestas`) VALUES ('$name_client', '$name_school', '$funcionality', '$confiabilidad', '$usability', '$efficiency', '$maintenance', '$portability', '$safety', '$compatibility', '$average', '$id_test')";

    $resultado=mysqli_query ($conex,$insertPersonTest);


    if ($resultado) {
        success();
        mysqli_close($conex);
    }else{
        fail();
        mysqli_close($conex);
    }

}else if (isset($_POST['idTest'])){

    $id_test =  $_POST['idTest'];
    $csltNumTest= "SELECT count(id_people) as tests FROM people where encuestas_id_Encuestas=".$id_test;
    $excNumTest = mysqli_query($conex ,$csltNumTest) or die(mysqli_error($conex));


    foreach ($excNumTest as $testn){
        echo $testn['tests']." ";
    }

    $csltAverTest= "SELECT sum(average)/count(id_people) as averg FROM people where encuestas_id_Encuestas=".$id_test;
    $excAverTest = mysqli_query($conex ,$csltAverTest) or die(mysqli_error($conex));

    foreach ($excAverTest as $testa){
        if ($testa['averg'] == null){
            echo "0";
        }else{
            echo $testa['averg'];
        }
    }

}




