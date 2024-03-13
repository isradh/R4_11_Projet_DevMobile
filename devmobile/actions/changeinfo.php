<?php
// Activation des erreurs PDO et affichage des erreurs PHP
error_reporting(E_ALL);
ini_set('display_errors', 1);

header('Content-Type: application/json');
include_once '../config/Database.php';

$json = json_decode(file_get_contents('php://input'), true);

// Log des données reçues
error_log('Données reçues: ' . print_r($json, true));

$result = array();

try {
    // Connexion à la base de données avec PDO
    $bdd = new PDO('mysql:host=localhost;dbname=bd_powerhouse;charset=utf8', 'root', 'root');
    $bdd->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // Activer la gestion des erreurs

    // Vérifier si la connexion est établie
    if ($bdd) {
        // Si les données JSON sont présentes
        if (!empty($json)) {
            // Extraction des données
            $email = filter_var($json['email'], FILTER_SANITIZE_EMAIL);
            error_log('Email extrait: ' . $email);

            $nom = isset($json['nom']) ? htmlspecialchars($json['nom']) : null;
            $prenom = isset($json['prenom']) ? htmlspecialchars($json['prenom']) : null;
            $etage = isset($json['etage']) ? htmlspecialchars($json['etage']) : null;
            $superficie = isset($json['superficie']) ? htmlspecialchars($json['superficie']) : null;

            // Log des données extraites
            error_log('Email: ' . $email . ', Nom: ' . $nom . ', Prenom: ' . $prenom . ', Etage: ' . $etage . ', Superficie: ' . $superficie);

            // Construction de la requête de mise à jour pour la table users
            $updateQueryUsers = "UPDATE users SET ";
            $updateDataUsers = array();

            // Vérification des champs et ajout à la requête si non vides
            if (!empty($nom)) {
                $updateDataUsers[] = "nom = :nom";
            }
            if (!empty($prenom)) {
                $updateDataUsers[] = "prenom = :prenom";
            }

            // Si des données à mettre à jour sont présentes pour la table users
            if (!empty($updateDataUsers)) {
                $updateQueryUsers .= implode(", ", $updateDataUsers);
                $updateQueryUsers .= " WHERE email = :email";

                // Préparation de la requête SQL pour la mise à jour de la table users
                $stmtUsers = $bdd->prepare($updateQueryUsers);
                $stmtUsers->bindParam(':email', $email, PDO::PARAM_STR);

                // Binding des paramètres si nécessaire pour la table users
                if (!empty($nom)) {
                    $stmtUsers->bindParam(':nom', $nom, PDO::PARAM_STR);
                }
                if (!empty($prenom)) {
                    $stmtUsers->bindParam(':prenom', $prenom, PDO::PARAM_STR);
                }

                // Exécution de la mise à jour de la table users
                $stmtUsers->execute();
                error_log('Requête de mise à jour pour la table users: ' . $updateQueryUsers);
            }

            // Construction de la requête de mise à jour pour la table habitats
            $updateQueryHabitats = "UPDATE habitats SET ";
            $updateDataHabitats = array();

            // Vérification des champs et ajout à la requête si non vides
            if (!empty($etage)) {
                $updateDataHabitats[] = "etage = :etage";
            }
            if (!empty($superficie)) {
                $updateDataHabitats[] = "superficie = :superficie";
            }

            // Si des données à mettre à jour sont présentes pour la table habitats
            if (!empty($updateDataHabitats)) {
                $updateQueryHabitats .= implode(", ", $updateDataHabitats);
                $updateQueryHabitats .= " WHERE idUser IN (SELECT idUser FROM users WHERE email = :email)";

                // Préparation de la requête SQL pour la mise à jour de la table habitats
                $stmtHabitats = $bdd->prepare($updateQueryHabitats);
                $stmtHabitats->bindParam(':email', $email, PDO::PARAM_STR);

                // Binding des paramètres si nécessaire pour la table habitats
                if (!empty($etage)) {
                    $stmtHabitats->bindParam(':etage', $etage, PDO::PARAM_INT);
                }
                if (!empty($superficie)) {
                    $stmtHabitats->bindParam(':superficie', $superficie, PDO::PARAM_INT);
                }

                // Exécution de la mise à jour de la table habitats
                $stmtHabitats->execute();
                error_log('Requête de mise à jour pour la table habitats: ' . $updateQueryHabitats);
            }

            // Vérification si la mise à jour a réussi pour les deux tables
            $rowCountUsers = $stmtUsers ? $stmtUsers->rowCount() : 0;
            $rowCountHabitats = $stmtHabitats ? $stmtHabitats->rowCount() : 0;

            if ($rowCountUsers > 0 || $rowCountHabitats > 0) {
                $result["success"] = true;
                $result["message"] = "Informations mises à jour avec succès.";
            } else {
                $result["success"] = false;
                $result["message"] = "Échec de la mise à jour des informations. Veuillez réessayer.";
            }
        } else {
            $result["success"] = false;
            $result["message"] = "Veuillez fournir des informations à mettre à jour.";
        }
    } else {
        $result["success"] = false;
        $result["message"] = "Erreur de connexion à la base de données.";
    }
} catch (PDOException $e) {
    // En cas d'erreur PDO, enregistrer l'erreur et retourner un message d'erreur
    error_log('Erreur PDO lors de la mise à jour des informations : ' . $e->getMessage(), 0);
    $result["success"] = false;
    $result["message"] = "Erreur PDO lors de la mise à jour des informations. Veuillez réessayer.";
}

// Retourner le résultat encodé en JSON
echo json_encode($result);
?>
