Projet Hadoop : Filtrage Collaboratif

Description

Ce projet implémente un système de filtrage collaboratif en trois étapes à l'aide de Hadoop MapReduce. Chaque job est conçu pour traiter et analyser les relations entre utilisateurs dans un réseau social afin de générer des recommandations d'amitié basées sur les connexions partagées.

Vue d'ensemble des Jobs :

Job 1 : Analyse et structuration des relations utilisateur à partir des données brutes.

Job 2 : Identification et comptage des relations communes entre les paires d'utilisateurs.

Job 3 : Génération et classement des recommandations d'amitié pour chaque utilisateur en fonction des connexions communes.

Prérequis

Hadoop installé et configuré.

Docker installé pour exécuter le conteneur Hadoop.

Java (version 8 ou supérieure).

Une version clonée du dépôt de code de départ.

Instructions de configuration

1. Construire l'image Docker

Exécutez la commande suivante dans le répertoire racine du projet pour construire l'image Docker :

docker build -t hadoop-image .

2. Lancer le conteneur Hadoop

Démarrez le conteneur Hadoop avec les ports et volumes nécessaires :

docker run -it --rm \
-p 8088:8088 -p 9870:9870 -p 9864:9864 \
-v $(pwd)/data/relationships:/data \
-v $(pwd)/jars:/jars \
hadoop-image

3. Compiler et construire les JARs

Dans le conteneur Hadoop, compilez le code source et générez les fichiers JAR pour chaque job :

cd /data
javac -d classes -cp $(hadoop classpath) src/main/java/org/epf/hadoop/colfil1/*.java
jar -cvf /jars/tpfinal-<prenom>_<nom>_job1.jar -C classes .

javac -d classes -cp $(hadoop classpath) src/main/java/org/epf/hadoop/colfil2/*.java
jar -cvf /jars/tpfinal-<prenom>_<nom>_job2.jar -C classes .

javac -d classes -cp $(hadoop classpath) src/main/java/org/epf/hadoop/colfil3/*.java
jar -cvf /jars/tpfinal-<prenom>_<nom>_job3.jar -C classes .

4. Charger les données dans HDFS

Copiez les données d'entrée dans HDFS :

hdfs dfs -put /data/data.txt /data.txt

Instructions d'exécution

Job 1 : Analyse et structuration des relations utilisateur

Exécutez le Job 1 pour analyser les données brutes et générer une liste de relations pour chaque utilisateur :

hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar /data.txt /output/job1

Job 2 : Comptage des relations communes

Exécutez le Job 2 pour identifier les relations communes entre les paires d'utilisateurs :

hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job2-1.0.jar /output/job1 /output/job2

Job 3 : Génération des recommandations d'amitié

Exécutez le Job 3 pour générer les recommandations d'amitié pour chaque utilisateur :

hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job3-1.0.jar  /output/job2 /output/job3

Consultation des résultats

Résultats du Job 1

Pour afficher les relations utilisateur structurées :

hdfs dfs -cat /output/job1/part-r-*

Résultats du Job 2

Pour afficher les relations communes entre les paires d'utilisateurs :

hdfs dfs -cat /output/job2/part-r-*

Résultats du Job 3

Pour afficher les meilleures recommandations pour chaque utilisateur :

hdfs dfs -cat /output/job3/part-r-*

