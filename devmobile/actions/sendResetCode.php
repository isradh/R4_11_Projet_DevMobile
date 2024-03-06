<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

require '../../../../../Users/isra/vendor/autoload.php'; // Assurez-vous que le chemin vers autoload.php est correct

header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);
$result = [];

if (isset($json['email'])) {
    $email = filter_var($json['email'], FILTER_SANITIZE_EMAIL);
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $result["success"] = false;
        $result["erreur"] = "Email non valide.";
        echo json_encode($result);
        exit;
    }

    $code = rand(100000, 999999);
    $expires = new DateTime("+1 hour");

    $stmt = $bdd->prepare("UPDATE users SET reset_code = ?, code_expiration = ? WHERE email = ?");
    $stmt->bindParam(1, $code);
    $formattedExpires = $expires->format('Y-m-d H:i:s');
    $stmt->bindParam(2, $formattedExpires);
    $stmt->bindParam(3, $email);
    $stmt->execute();

    if ($stmt->rowCount() > 0) {
        $mail = new PHPMailer(true);
        try {
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com'; // Spécifiez le serveur SMTP
            $mail->SMTPAuth   = true;
            $mail->Username   = 'powerhouse.verif@gmail.com'; // SMTP username
            $mail->Password   = 'vxds qrff rtcf zana'; // SMTP password
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;

            $mail->setFrom('powerhouse.verif@gmail.com', 'PowerHouse');
            $mail->addAddress($email); // Ajoutez un destinataire

            // Contenu
            $mail->isHTML(true); // Définissez le format de l'email à HTML
            $mail->Subject = 'Votre code de réinitialisation';
            $mail->Body    = "Bonjour ! <br> <br> Voici votre code vous permettant de changer votre mot de passe : <b>$code</b>
                            <br> <br>le code de vérification à une durée de validité de seulement <b>30 minutes<b>
                            <br> <br>À bientôt sur PowerHouse !
                            <br> Ceci est un mail automatique, merci de ne pas y répondre.";

            $mail->send();
            $result["success"] = true;
            $result["message"] = "Email envoyé avec succès.";
        } catch (Exception $e) {
            $result["success"] = false;
            $result["erreur"] = "L'email n'a pas pu être envoyé. Erreur de PHPMailer: {$mail->ErrorInfo}";
        }
    } else {
        $result["success"] = false;
        $result["erreur"] = "Email non trouvé.";
    }
} else {
    $result["success"] = false;
    $result["erreur"] = "Veuillez fournir un email.";
}

echo json_encode($result);
?>
