## Individual Analysis Report

Among 3 methods, "/post" endpoint performed best and "/postandref" endpoint performed worst. The reason is that third route (/postandref) creates a reference separately in another table and links it with foreign key via POST which takes more time.