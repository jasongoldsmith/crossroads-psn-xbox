<?php
require_once 'XboxLiveClient.php';

if (!isset($_GET['access_token'])) {
  echo "{\"error\":\"no access_token found\"}";
    exit;
}

try {
    //$live = XboxLiveClient::withUsernameAndPassword($username, $password);
  $live = XboxLiveClient::withAccessToken($_GET['access_token']);
    //$live = XboxLiveClient::withAccessToken("EwAIA61DBAAUGCCXc8wU/zFu9QnLdZXy+YnElFkAAftsC3U4Ur2cbjxLzbug+7d7BaQChm5gIW5lZ3DyI6m1ynedo8e+nPrkwbDUdT8qa5lf8KGPSW8FbcgXirlciE99EE4vNNonOesBcPaZQXrK6aeRznPLa8zcKJEUmBzekSB+EX5CP3jEwk/s5DEgIV/vY98PDkrfDz30/gf5nnGr5tRKRQ7Rd8WwuciFfcK574dklbYQmgBe1quM8rxFB1VyZfVyLAzH7VgvI4LVYvQR03Q/sWUWiFBPgtp7U0Y9gc75GtX4cGUEndr9gbz0q2V67LwBUCdrwq60OFq4ajCF3gKYn0VvVr2YZQQa+38DiZDkh9dna+CSe3RhyUbo0ZEDZgAACJrgOMJUe1kf2AFyec5nZcqllZKycDcNO6iByE5qqRKCuls/gsEcQS/K3yYP4IuOEVfkZzVwkINNvD6oA/2HzuhQZkYVP8aRsjJjC1B0AMgpAl0tqvfJU2KB8+zteyipcwisG8kwBLaELLuh3itJjn3MntSlnbRXAGdDPx8i113nw/5Sh0mqABF1OWnp1ADzoX6kmifrcnP5ZMBroy5tMZ7aSho6bgvt5ZSavyWeFsrprTn9wwI04rzjFema3BGtqN24kqWFlvUNxdF2xZfksbZtQPq0wUwg9Sm0OAZXCLVf/4hzO+en/Hq7H0raQ9JgRiyrl7u4JZJwIe2rU4y8MB1+oYRoiE96hDXi1WrjxMef+4EIxYxiaxfL2LcZ0nzGV3GKSTLz7GdvYafNXAS6k2B7JYZsNHYG7Ey+AFxdzVyx1NEKO/brVtR59YMO/DZ/OwVmdvU1J3wzl6tb0c5UHa/TmvAKNbDnJaEKWDCLEKW9CTZ2CT6CvOwkhNDJPd9mxl19ZbvgObm+8HDPfS+p4Y2wFiX9MocyYmQkemHYCjGrHjrxd45QvmD5QZ/CmXnoV70fBcyqgEj0TKGP35cx3v5vaw0USRGN+wqJeetZv+dzuFpo04FEPIVwo/5iLc2JYN/kEAI=");
}
catch (Exception $e) {
  printf("Couldn't go with u/p with message '%s'\n", $e->getMessage());
  exit;
}

$ret = array('gamertag' => $live->gamertag, 'xuid' => $live->xuid, 'authorization_header' => $live->authorization_header, 'authorization_expires' => $live->authorization_expires);
echo json_encode($ret);



