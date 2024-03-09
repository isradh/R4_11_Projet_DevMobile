<?php

header('Content-Type: application/json');

// Connexion à la base de données
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "bd_powerhouse";

try {
    $bdd = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $bdd->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $e) {
    die(json_encode(array("error" => "Database connection failed: " . $e->getMessage())));
}

$sql = "SELECT u.idUser, u.nom, u.prenom, h.etage
        FROM users u
        INNER JOIN habitats h ON u.idUser = h.idUser";

try {
    $result = $bdd->query($sql);

    $data = array();

    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        $data[] = $row;
    }

    $bdd = null;

    echo json_encode($data);
} catch(PDOException $e) {
    die(json_encode(array("error" => "Une erreur est survenue: " . $e->getMessage())));
}

?>
