# Analysis

"/updatebyname" endpoint works worst and "/updatebyid" endpoint works best according to the performance results. This happens because id field is indexed and it makes columns faster to query.