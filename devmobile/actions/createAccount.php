<?php

header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

$result = array();

if (isset($json['nom']) && isset($json['prenom']) && isset($json['email']) && isset($json['mdp']) && isset($json['etage']) && isset($json['superficie'])) {
    $nom = htmlspecialchars($json['nom']);
    $prenom = htmlspecialchars($json['prenom']);
    $email = htmlspecialchars($json['email']);
    $mdp = htmlspecialchars($json['mdp']);
    $etage = intval($json['etage']);
    $superficie = intval($json['superficie']);

    if ($nom == "" || $prenom == "" || $email == "" || $mdp == "" || $etage == "" || $superficie == "") {
        $result["success"] = false;
        $result["erreur"] = "Veuillez compléter tous les champs.";
    } else {
        $checkaccount = $bdd->prepare('SELECT idUser FROM users WHERE email = ?');
        $checkaccount->execute(array($email));

        if ($checkaccount->rowCount() > 0) {
            $result["success"] = false;
            $result["erreur"] = "Un compte avec ce mail existe déjà";
        } else {
            if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                $result["success"] = false;
                $result["erreur"] = "Veuillez saisir une adresse email valide";
            }else if ($etage > 50){
                $result["success"] = false;
                $result["erreur"] = "Le nombre d'étages ne peut pas dépasser 50.";
            } else if ($superficie < 9 || $superficie > 600) {
                $result["success"] = false;
                $result["erreur"] = "La superficie doit être au minimum de 9 et au maximum de 600.";
            } else if (strlen($mdp) < 8 || !preg_match('/[A-Z]/', $mdp) || !preg_match('/[0-9]/', $mdp)){
                $result["success"] = false;
                $result['erreur'] = "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre";
            } else {
                $mdpHashed = password_hash($mdp, PASSWORD_DEFAULT);
                try {
                    $createAccount = $bdd->prepare("INSERT INTO users (nom, prenom, email, mdp) VALUES (?, ?, ?, ?)");
                    $createAccount->execute(array($nom, $prenom, $email, $mdpHashed));

                    $lastIdUser = $bdd->lastInsertId();

                    $createHabitat = $bdd->prepare("INSERT INTO habitats (idUser, etage, superficie) VALUES (?, ?, ?)");
                    $createHabitat->execute(array($lastIdUser, $etage, $superficie));

                    $result["success"] = true;
                } catch (Exception $e) {
                    $result["success"] = false;
                    $result["erreur"] = "Erreur lors de la création du compte : " . $e->getMessage();
                }
            }
        }
    }
} else {
    $result["success"] = false;
    $result["erreur"] = "Veuillez compléter tous les champs.";
}

echo json_encode($result);
