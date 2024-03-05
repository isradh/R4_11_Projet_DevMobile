<?php
header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true); 
$result = array(); // Initialisation de $result

if(isset($json['email']) && isset($json['mdp'])){ 
    $email = htmlspecialchars($json['email']); 
    $mdp = htmlspecialchars($json['mdp']);


    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $result["success"] = false;
        $result["erreur"] = "Veuillez saisie une adresse email valide";
    }
    $getUser = $bdd->prepare("SELECT * FROM users WHERE email = ?"); 
    $getUser->execute([$email]); 

    if ($getUser->rowCount() > 0) {
        $user = $getUser->fetch();

        if(password_verify($mdp, $user['mdp'])){
            $result["success"] = true;
        } else {
            $result["success"] = false;
            $result["erreur"] = "Mot de passe incorrect";
        }
    } else {
        $result["success"] = false;
        $result["erreur"] = "Aucun compte trouvé avec cet email";
    }
} else {
    $result["success"] = false;
    $result["erreur"] = "Veuillez remplir tous les champs";
}

echo json_encode($result);
?>
