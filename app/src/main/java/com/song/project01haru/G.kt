package com.song.project01haru

class G {
    companion object{
        var aaa:String="Hello"
    }
}
//
//<?php
//
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//
//
////exp
//$date=$_GET['date'];
//$inc=$_GET['incTotal'];
//$exp=$_GET['expTotal'];
//$total=$_GET['total'];
//$account=$_GET['account'];
//$type=$_GET['type'];
//$category=$_GET['category'];
//$note=$_GET['note'];
//$amount=$_GET['amount'];
//
//$expsql="INSERT INTO haru_exp(date,incTotal,expTotal,total,account,type,category,note,amount) VALUES('$date','$inc','$exp','$total','$account','$type','$category','$note',$amount')";
//$expResult=mysqli_query($db,$expsql);
//
//if($expResult) echo "success";
//else echo "failed";
//
//
//mysqli_close($db);
//?>
//
//<?php
//header('Content-Type:application/json; charseet=utf-8');
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//$day=$_GET['date'];
//$sql="SELECT * FROM haru_todo WHERE date='$day'";
//$result=mysqli_query($db,$sql);
//
//$row_num=mysqli_num_rows($result);
//
//
//$rows=array();
//for($i=0; $i<$row_num; $i++){
//    $row=mysqli_fetch_array($result,MYSQLI_ASSOC);
//    $rows[$i]=$row;
//}
//
//echo json_encode($rows);
//
//mysqli_close($db);
//
//?>





////login
//<?php
//
//
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//
////login
//$actNum=$_GET['act'];
//$email=$_GET['email'];
//$userName=$_GET['name'];
//$img=$_GET['img'];
//$loginsql="INSERT INTO haru_login(act,email,name,img) VALUES('$actNum','$email','$userName','$img')";
//$loginResult=mysqli_query($db,$loginsql);
//
//if($loginResult) echo "success";
//else echo "failed";
//
//mysqli_close($db);
//?>
//
//<?php
//header('Content-Type:application/json; charseet=utf-8');
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//
//$sql="SELECT * FROM haru_login";
//$result=mysqli_query($db,$sql);
//
//$row_num=mysqli_num_rows($result);
//
//
//$rows=array();
//for($i=0; $i<$row_num; $i++){
//    $row=mysqli_fetch_array($result,MYSQLI_ASSOC);
//    $rows[$i]=$row;
//}
//
//echo json_encode($rows);
//
//mysqli_close($db);
//
//?>


////todo
//<?php
//
//
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//
//
//// todo
//$date=$_GET['date'];
//$time=$_GET['time'];
//$todo=$_GET['todo'];
//$sql="INSERT INTO haru_todo(date,time,todo) VALUES('$date','$time','$todo')";
//$todoResult=mysqli_query($db,$sql);
//
//if($todoResult) echo "success";
//else echo "failed";
//
//
//// skd
//// $datetime=$_GET['datetime'];
//// $skd=$_GET['skd'];
//// $note=$_GET['note'];
//
//// $skdSql="INSERT INTO haru_skd(datetime,skd,note) VALUES('$datetime','$skd','$note')";
//// $skdResult=mysqli_query($db,$skdSql);
//
//// if($skdResult) echo "success";
//// else echo "failed";
//
//
//// //event
//// $dateEvent=$_GET['dateEvt'];
//// $timeEvent=$_GET['timeEvt'];
//// $event=$_GET['event'];
//
//// $eventSql="INSERT INTO haru_event(dateEvt,timeEvt,event) VALUES('$dateEvent','$timeEvent','$event')";
//// $eventResult=mysqli_query($db,$eventSql);
//
//// if($eventResult) echo "success";
//// else echo "failed";
//
//// //diary
//// $icon=$_GET['icon'];
//// $content=$_GET['content'];
//// $img=$_GET['img'];
//// $maplt=$_GET['map_lat'];
//// $maplng=$_GET['map_long'];
//
//// $diarySql="INSERT INTO haru_diary(icon,content,img,map_lat,map_long) VALUES('$icon','$content','$img','$maplt','$maplng')";
//// $diaryResult=mysqli_query($db,$diarySql);
//
//// if($diaryResult) echo "success";
//// else echo "failed";
//
//mysqli_close($db);
////?>
//<?php
//header('Content-Type:application/json; charseet=utf-8');
//
//$day=$_GET['date'];
//
//$db=mysqli_connect("localhost","mins22","snim222!","mins22");
//mysqli_query($db,"set names utf8");
//
//$sql="SELECT * FROM haru_todo WHERE date='$day'";
//$result=mysqli_query($db,$sql);
//
//$row_num=mysqli_num_rows($result);
//
//
//$rows=array();
//for($i=0; $i<$row_num; $i++){
//    $row=mysqli_fetch_array($result,MYSQLI_ASSOC);
//    $rows[$i]=$row;
//}
//
//echo json_encode($rows);
//
//mysqli_close($db);
//
//?>