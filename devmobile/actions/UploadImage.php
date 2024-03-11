<?php
$con = mysqli_connect("localhost", "root", "root", "bd_powerhouse");

if (!empty($_POST['image']) && !empty($_POST['nom']) && !empty($_POST['wattage']) && !empty($_POST['idUser'])) {
    $path = 'images/' . date("d-m-Y") . '-' . time() . '-' . rand(100, 1000000) . '.jpg';
    $nom = htmlspecialchars($_POST['nom']);
    $wattage = intval($_POST['wattage']);
    $idUser = intval($_POST['idUser']);


    $query = "SELECT COUNT(*) AS count FROM appliances WHERE nom = '$nom' AND idUser = '$idUser'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_assoc($result);
    $imageCount = intval($row['count']);

    if ($imageCount > 0) {
        echo json_encode(array("message" => "Une image avec le même nom existe déjà pour cet utilisateur"));
    } else {
        if (file_put_contents($path, base64_decode($_POST['image']))) {
            $sql = "INSERT INTO appliances (path, nom, wattage, idUser) VALUES ('$path', '$nom', '$wattage', '$idUser')";
            if (mysqli_query($con, $sql)) {
                echo json_encode(array("message" => "L'image a bien été téléchargée !"));
            } else {
                echo json_encode(array("message" => "L'image n'a pas pu être téléchargée"));
            }
        } else {
            echo json_encode(array("message" => "L'image n'a pas pu être téléchargée"));
        }
    }
} else {
    echo json_encode(array("message" => "Aucune image trouvée"));
}
?>
