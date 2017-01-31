<?php

require_once("autoload.php");

if (!isset($_GET['npsso'])) {
	echo "{\"error\":\"no npsso found\"}";
    exit;
}

try 
{
 //   $account = new \PSN\Auth("tina@crossroadsapp.co", "Traveler1990");

    $account = new \PSN\Auth($_GET['npsso']);
	//$account = new \PSN\Auth("skqkDSZ2jeN5dWpLDTOyZEsZOd9yBEJgzKlGHspwJx16PEijA8mvY5HLxdoWu1Wr");

} 
catch (\PSN\PSNAuthException $e)
{
    header("Content-Type: application/json");
    die($e->GetError());
}

$tokens = $account->GetTokens();

$user = new \PSN\User($tokens);

//echo "{\"user\":{\"userId\":\"" . $user->Me()->profile->onlineId . "\"},\"token\":\"" + json_encode($tokens) + "\"}";

$tokens["userId"] = $user->Me()->profile->onlineId;
echo json_encode($tokens);