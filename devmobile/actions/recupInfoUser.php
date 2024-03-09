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

// Vérifie si l'id de l'utilisateur est fourni
if(isset($_GET['userId'])) {
    $userId = $_GET['userId'];
    $sql = "SELECT u.nom, u.prenom, u.email, u.mdp, h.etage, h.superficie
    FROM users u
    INNER JOIN habitats h ON u.idUser = h.idUser
    WHERE u.idUser = :userId";

    
    try {
        $stmt = $bdd->prepare($sql);
        $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
        $stmt->execute();

        $data = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $data[] = $row;
        }

        $bdd = null;

        echo json_encode($data);
    } catch(PDOException $e) {
        die(json_encode(array("error" => "An error occurred: " . $e->getMessage())));
    }
} else {
    echo json_encode(array("error" => "User ID is required."));
}

?>
