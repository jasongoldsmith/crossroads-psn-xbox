<?php
require_once("autoload.php");

if (!isset($_GET['oauth']) || !isset($_GET['refresh'])) {
	echo "{\"error\":\"no tokens found\"}";
    exit;
}
else if (!isset($_GET['user'])) {
	echo "{\"error\":\"user can not be empty\"}";
    exit;
}
else if (!isset($_GET['message'])) {
	echo "{\"error\":\"message can not be empty\"}";
    exit;
}
$tokens = array(
            "oauth" => $_GET['oauth'],
            "refresh" => $_GET['refresh']
        );

try 
{
    $message = new \PSN\Messaging($tokens);
    $ret = $message->TextMessage($_GET['user'], $_GET['message']);
    echo "success";
} 
catch (\PSN\PSNAuthException $e)
{
    header("Content-Type: application/json");
    die($e->GetError());
}