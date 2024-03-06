<?php
header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);
$result = [];

if (isset($json['email']) && isset($json['newPassword'])) {
    $email = htmlspecialchars($json['email']);
    $newPassword = password_hash(htmlspecialchars($json['newPassword']), PASSWORD_DEFAULT); // Sécurisation du nouveau mot de passe

    $conn = (new Database())->getConnection();
    $stmt = $conn->prepare("UPDATE users SET mdp = ?, reset_code = NULL, code_expiration = NULL WHERE email = ?");
    $stmt->bind_param("ss", $newPassword, $email);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        $result["success"] = true;
        $result["message"] = "Mot de passe réinitialisé.";
    } else {
        $result["success"] = false;
        $result["erreur"] = "Erreur de réinitialisation ou email non trouvé.";
    }
} else {
    $result["success"] = false;
    $result["erreur"] = "Veuillez compléter tous les champs.";
}

echo json_encode($result);
?>
