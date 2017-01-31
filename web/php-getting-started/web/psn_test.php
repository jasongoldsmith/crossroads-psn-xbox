<?php

require_once("autoload.php");
/*
if (!isset($_GET['npsso'])) {
	echo "{\"error\":\"no npsso found\"}";
    exit;
}
*/
try 
{
 	//$account = new \PSN\Auth("tina@crossroadsapp.co", "Traveler1990");

    //$account = new \PSN\Auth($_GET['npsso']);
	$account = new \PSN\Auth("96RvU0HtFxxqpkMPTt7Wf27d6rDLN1TpH1Hjjo3h5nMiSaa2yG7hnXoJ1Lb0Olno");

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