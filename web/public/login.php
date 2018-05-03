<?php

require_once __DIR__ . '/includes/header.php';


$error = false;
$db = new PDO('mysql:host=mn26.webd.pl;dbname=marekb93_rpggame', 'marekb93_rpggame', 'xBGG)2Jn&b?E?kC+');
if (! empty($_POST)) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    if($username != '' && $password != ''){
	    $sql = "SELECT * FROM players WHERE login = '" . $username . "' AND password = '" . $password . "' ";
		$user = $db->query($sql)->fetchObject();

		if (false === $user) {
		    echo "User does not exists or bad password!";
        } else {
            $_SESSION['user'] = $user;
            header("Location: /table.php");
        }
    }
}

?>

<br><br><br>
	<style>
	.container {
		margin: auto;
		width: 70%;
		border: 3px solid grey;
		padding: 10px;
	}
	</style>
	<div class="container">
		<form method="post">
			<div class="form-group">
				<label for="exampleInputEmail1">Login</label> <input type="login"
					name="username" class="form-control" id="exampleInputEmail1"
					aria-describedby="emailHelp" placeholder="Enter login"> <small
					id="emailHelp" class="form-text text-muted">Enter your login</small>
			</div>
			<div class="form-group">
    			<label for="exampleInputPassword1">Password</label> <input
					type="password" name="password" class="form-control"
					id="exampleInputPassword2" placeholder="Enter password">
			</div>
	
			<button id="submit" value="Create account" class="btn btn-primary">Log in</button>
			  
		</form>
	</div>



<?php

require_once __DIR__.'/includes/footer.php';