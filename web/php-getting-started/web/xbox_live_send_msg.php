<?php
require_once 'XboxLiveClient.php';

if (!isset($_GET['access_token'])) {
  echo "{\"error\":\"no access_token found\"}";
    exit;
}
else if (!isset($_GET['gamertags'])) {
	echo "{\"error\":\"user can not be empty\"}";
    exit;
}
else if (!isset($_GET['message'])) {
	echo "{\"error\":\"message can not be empty\"}";
    exit;
}

try {
  $live = XboxLiveClient::withAccessToken($_GET['access_token']);

  $gts = explode(",",$_GET['gamertags']);
  
  foreach($gts AS $gt) {
    $recipients[] = $live->fetchXuidForGamertag($gt);
  }

  $live->sendMessage($recipients, $_GET['message']);
  echo "success";
}
catch (Exception $e) {
  printf("Couldn't go with u/p with message '%s'\n", $e->getMessage());
  exit;
}

/*

$ret = array('gamertag' => $live->gamertag, 'xuid' => $live->xuid, 'authorization_header' => $live->authorization_header, 'authorization_expires' => $live->authorization_expires);
echo json_encode($ret);

*/

