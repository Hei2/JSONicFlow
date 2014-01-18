<%@ page import="java.util.HashMap" %>
<jsp:useBean id="songSession" class="beans.SongSearch" scope="session"/>
<jsp:setProperty name="songSession" property="*"/>
<jsp:useBean id="song" class="beans.main" scope="session"/>
<HTML>
<BODY bgcolor="Black">
<% HashMap<String, String> songInfo = song.getSongInfo(songSession.getQuery()); %>
<center><font color="White"><% out.println("Song Query: " + songSession.getQuery()); %></font><BR>
<table border="1" bgcolor="White">
	<tr>
		<td>Song:</td>
		<td><% out.println(songInfo.get("SongName")); %></td>
	</tr>
	<tr>
		<td>Artist:</td>
		<td><% out.println(songInfo.get("ArtistName")); %></td>
	</tr>
	<tr>
		<td>Album:</td>
		<td><% out.println(songInfo.get("AlbumName")); %></td>
	</tr>
</table><BR>
	
<object width="250" height="40">
	<param name="movie" value="http://grooveshark.com/songWidget.swf">
	<param name="wmode" value="window">
	<param name="allowScriptAccess" value="always">
	<param name="flashvars" value="hostname=cowbell.grooveshark.com&amp;songIDs=<% out.println(songInfo.get("SongID")); %>&amp;style=metal&amp;p=1">
	<embed src="http://grooveshark.com/songWidget.swf" type="application/x-shockwave-flash" width="250" height="40" flashvars="hostname=cowbell.grooveshark.com&amp;songIDs=<% out.println(songInfo.get("SongID")); %>&amp;style=metal&amp;p=1" allowscriptaccess="always" wmode="window">
</object><BR>

<a href="FirstPage.html"><button type="button">Back</button></a></center>
</BODY>
</HTML>