<?php
header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);
$result = [];

error_log("Contenu de la requête JSON : " . print_r($json, true));

if (isset($json['email']) && isset($json['resetCode'])) {
    $email = filter_var($json['email'], FILTER_SANITIZE_EMAIL);
    $resetCode = intval($json['resetCode']);

    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $result["success"] = false;
        $result["message"] = "Email non valide.";
        echo json_encode($result);
        exit;
    }

    error_log("Email extrait : " . $email);
    error_log("Code de réinitialisation extrait : " . $resetCode);

    // Formater la date actuelle dans le bon format
    $currentDate = date('Y-m-d H:i:s');

    // Utiliser la date formatée dans la requête SQL pour vérifier si le code n'a pas expiré
    $stmt = $bdd->prepare("SELECT * FROM users WHERE email = :email AND reset_code = :resetCode AND code_expiration > :currentDate");
    $stmt->bindParam(':email', $email, PDO::PARAM_STR);
    $stmt->bindParam(':resetCode', $resetCode, PDO::PARAM_INT);
    $stmt->bindParam(':currentDate', $currentDate, PDO::PARAM_STR);
    $stmt->execute();
    $user = $stmt->fetch();

    if ($user) {
        // Code correct et non expiré
        $result["success"] = true;
        $result["message"] = "Le code de réinitialisation est valide.";
    } else {
        // Code incorrect ou expiré
        $result["success"] = false;
        $result["message"] = "Le code de réinitialisation est incorrect ou expiré.";
    }
} else {
    $result["success"] = false;
    $result["message"] = "Veuillez fournir une adresse e-mail et un code de réinitialisation.";
}

// Afficher le résultat final dans le fichier de logs
error_log("Résultat final : " . print_r($result, true));

echo json_encode($result);
?>
