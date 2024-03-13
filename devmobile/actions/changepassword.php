<?php
header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

$result = array();

if (isset($json['email']) && isset($json['newPassword'])) {
    $email = filter_var($json['email'], FILTER_SANITIZE_EMAIL);
    $newPassword = htmlspecialchars($json['newPassword']);


    error_log("Email reçu : " . $email);
    error_log("Nouveau mot de passe reçu : " . $newPassword);


    if (empty($email) || empty($newPassword)) {
        $result["success"] = false;
        $result["message"] = "Veuillez fournir une adresse e-mail et un nouveau mot de passe.";
    } else {

        $newPasswordHashed = password_hash($newPassword, PASSWORD_DEFAULT);


        $stmt = $bdd->prepare("UPDATE users SET mdp = :newPassword WHERE email = :email");
        $stmt->bindParam(':email', $email, PDO::PARAM_STR);
        $stmt->bindParam(':newPassword', $newPasswordHashed, PDO::PARAM_STR);
        $stmt->execute();


        $rowCount = $stmt->rowCount();
        if ($rowCount > 0) {
            $result["success"] = true;
            $result["message"] = "Mot de passe modifié avec succès.";


            error_log("Mot de passe modifié pour l'utilisateur avec l'email : " . $email);
        } else {
            $result["success"] = false;
            $result["message"] = "Échec de la modification du mot de passe. Veuillez réessayer.";


            error_log("Échec de la modification du mot de passe pour l'utilisateur avec l'email : " . $email);
        }
    }
} else {
    $result["success"] = false;
    $result["message"] = "Veuillez fournir une adresse e-mail et un nouveau mot de passe.";


    error_log("Requête invalide. Veuillez fournir une adresse e-mail et un nouveau mot de passe.");
}


echo json_encode($result);
?>
