<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Convergens</title>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>

	<div style="overflow: hidden;">

		<div style="background-color: #f9f; float: left; padding: 20px;">
			<button name="all">Alle ydelser</button>
			<br /> <br /> CPR: <input type="text" name="cpr" value="2711900000" />
			<button name="getYdelserByCPR">Find ydelser med CPR</button>
			<br /> Type: <input type="text" name="type" value="Ydelse1" /> <br />
			<button name="getYdelserByTypeAndCPR">Find ydelser med type
				og CPR</button>
		</div>

		<div style="background-color: #9f9; float: left; padding: 20px;">
			<form id="postForm">
				<table>
					<tr>
						<td>ID:</td>
						<td><input type="text" name="createID" /></td>
					</tr>
					<tr>
						<td>CPR:</td>
						<td><input type="text" name="createCPR" value="2711900000" /></td>
					</tr>
					<tr>
						<td>Kr:</td>
						<td><input type="number" name="createKr" value="499" /></td>
					</tr>
					<tr>
						<td>Dato:</td>
						<td><input type="text" name="createDato" value="21-01-2014" /></td>
					</tr>
					<tr>
						<td>Type:</td>
						<td><input type="text" name="createType" value="Ydelse1" /></td>
					</tr>
					<tr>
						<td colspan="2"><input
							style="width: 100%; float: right; font-size: 400%;" type="submit"
							name="postSubmit" value="submit" /></td>
					</tr>
				</table>
			</form>
		</div>

		<div style="background-color: #ccc; float: left; padding: 20px;">
			<form id="deleteForm">
				<input type="number" name="delete" /> <input type="submit"
					name="deletBtn" value="Delete" />
			</form>
		</div>

	</div>

	<div style="background-color: #000">

		<ul></ul>

	</div>

	<script>
		$(document).ready(function() {

			$("#deleteForm").submit(function(e) {
				e.preventDefault();

				var id = $("input[name='delete']").val();

				$.ajax({
					url : "rest/ydelser/" + id,
					type : "delete"
				});
			});

			//Update or create resource depending on, that id is provided
			$("#postForm").submit(function(event) {

				event.preventDefault();

				var id = $("input[name='createID']").val();
				var cpr = $("input[name='createCPR']").val();
				var kr = $("input[name='createKr']").val();
				var dato = $("input[name='createDato']").val();
				var type = $("input[name='createType']").val();

				if (!$.isNumeric(id)) {
					$.post("rest/ydelser", $("#postForm").serialize());
				} else {
					$.ajax({
						url : "rest/ydelser/" + id,
						type : "put",
						data : {
							cpr : cpr,
							kr : kr,
							dato : dato,
							type : type
						}
					});
				}
			});

			/*** PINK ACTIONS ***/

			//Getting all ydelser when clicking the button "alle ydelser"
			//return type: json
			$("button[name='all']").click(function() {
				$.ajax({
					url : "rest/ydelser",
					type : "get",
					contentType : "application/json"
				}).success(function(data) {
					$("div ul").empty();
					$(data).each(function(i) {
						$("div ul").append("<li style='color: #fff;'>ID#"+data[i].id+" CPR:"+data[i].cpr+" har købt Ydelse: "+data[i].type+" for "+data[i].kr+" Kroner.</li>");
					});
					
				}).done(function() {
					//console.log(data);
				});
			});

			//Getting ydelser by CPR when clicking "find ydelser med CPR" button
			//return type: json
			$("button[name='getYdelserByCPR']").click(function() {

				//Getting the cpr value
				var cpr = $("input[name='cpr']").val();

				$.getJSON("rest/ydelser/" + cpr, function(data) {
					$("div ul").empty();
					$(data).each(function(i) {
						$("div ul").append("<li style='color: #fff;'>ID#"+data[i].id+" CPR:"+data[i].cpr+" har købt Ydelse: "+data[i].type+" for "+data[i].kr+" Kroner.</li>");
					});
				});

			});

			//Getting ydelser by Type and CPR when clicking "find ydelser med type og CPR" button
			//return type: json
			$("button[name='getYdelserByTypeAndCPR']").click(function() {

				//Getting the cpr value
				var cpr = $("input[name='cpr']").val();

				//Getting the type value
				var type = $("input[name='type']").val();

				$.getJSON("rest/ydelser/" + type + "/" + cpr, function(data) {
					$("div ul").empty();
					$(data).each(function(i) {
						$("div ul").append("<li style='color: #fff;'>ID#"+data[i].id+" CPR:"+data[i].cpr+" har købt Ydelse: "+data[i].type+" for "+data[i].kr+" Kroner.</li>");
					});
				});

			});

			/*** END OF PINK ACTIONS ***/

		});
	</script>

</body>
</html>