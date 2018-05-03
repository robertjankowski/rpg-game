<?php

require_once __DIR__ . '/includes/header.php';

$error = false;
$db = new PDO('mysql:host=mn26.webd.pl;dbname=marekb93_rpggame', 'marekb93_rpggame', 'xBGG)2Jn&b?E?kC+');
if (! empty($_POST)) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    $repeat_password = $_POST['password2'];
    $role = $_POST['role'];
    if($password == $repeat_password){
    	$db->query("INSERT INTO players (login, password, role, gold, skill, magic, exp) VALUES ('" . $username . "', '" . $password . "', '" . $role . "', 100, 1, 1, 1)");
    	echo "Account was created";
    	$error = false;
    } else {
    	$error = true;
    	echo "<script>console.log( 'wrong password' );</script>";
    	echo '<p style="color:#FF0000">Password is required.</p>';
    }
}
?>

<br><br><br>
	<style>
	.container {
		<?php if($error){ ?>
    		background-color : red;
 		<?php } ?>
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
					id="emailHelp" class="form-text text-muted">Login has to be
					available!</small>
			</div>
			<div class="form-group">
				<?php if($error){ ?>
    			<label for="exampleInputPassword1">Password</label> <input
					type="password" name="password" class="form-control"
					id="exampleInputPassword2" placeholder="Passwords must be the same">
 				<?php } ?>
 				<?php if(!$error){ ?>
    			<label for="exampleInputPassword1">Password</label> <input
					type="password" name="password" class="form-control"
					id="exampleInputPassword2" placeholder="Password">
 				<?php } ?>	
			</div>
			<div class="form-group">
				<?php if($error){ ?>
    			<label for="exampleInputPassword1">Repeat password</label> <input
					type="password" name="password2" class="form-control"
					id="exampleInputPassword2" placeholder="Passwords must be the same">
 				<?php } ?>
 				<?php if(!$error){ ?>
    			<label for="exampleInputPassword1">Repeat password</label> <input
					type="password" name="password2" class="form-control"
					id="exampleInputPassword2" placeholder="Password">
 				<?php } ?>	
			</div>
			<div class="form-check">
				<input class="form-check-input" value="knight" type="radio"
					name="role" id="exampleRadios1" value="option1" checked> <label
					class="form-check-label" for="exampleRadios1"> Knight </label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" value="sorcerer"
					name="role" id="exampleRadios2" value="option2"> <label
					class="form-check-label" for="exampleRadios2"> Sorcerer </label>
			</div>
	
	
			<button type="submit" value="Create account" class="btn btn-primary">Submit</button>
		</form>
	</div>
<?php

require_once __DIR__.'/includes/footer.php';