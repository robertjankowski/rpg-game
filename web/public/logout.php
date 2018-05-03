<?php

require_once __DIR__ . '/includes/header.php';

$_SESSION['user'] = false;
header('Location: /index.php');
