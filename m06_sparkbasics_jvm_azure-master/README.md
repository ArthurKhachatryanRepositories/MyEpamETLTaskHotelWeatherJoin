* Add your code in `src/main/`
* Test your code with `src/tests/`
* Package your artifacts
* Modify dockerfile if needed
* Build and push docker image
* Deploy infrastructure with terraform
```
terraform init
terraform plan -out terraform.plan
terraform apply terraform.plan
....
terraform destroy
```
* Launch Spark app in cluster mode on Kubernetes Cluster
```
spark-submit \
    --master k8s://https://<k8s-apiserver-host>:<k8s-apiserver-port> \
    --deploy-mode cluster \
    --name sparkbasics \
    --conf spark.kubernetes.container.image=<spark-image> \
    ...
```

```
Homework with screenshots and comments.

I wrote a spark job application
to create a Spark etl job
to read data from an azure data lake storage gen 2 container.

I checked the hotel data for incorrect (null) values (latitude and longitude). For incorrect values, I have mapped (latitude and longitude) to 
OpenCage Geocoding API in job on fly (via REST API).

I generated a geohash by latitude and longitude using libraries of geohashes 4 characters long in an additional column.
I combined the weather and hotel data using a generated geohash of 4 characters.

I have configured the infrastructure using terraform scripts from the module.

I have configured the memory and cores correctly.
 
I have saved the enriched data (combined data with all fields from both datasets) in a storage prepared using terraform Azure ADLS gen2, keeping the data split in parquet format in the “data” container.
```

* see the link of git.epam.com app repo
* https://git.epam.com/arthur_khachatryan/m06_sparkbasics_jvm_azure.git

* see link to docker repo
* https://hub.docker.com/repository/docker/arturdockerhub/hotel_weathere

* the following images show the way of building the docker image
* and pushing it to docker hab container registry.
![docker image build](images/docker/docker_image_build.png?raw=true "docker image build")
![docker image push](images/docker/docker_image_push.png?raw=true "docker image push")

* the following image show the screenshot of my Docker Hub container registry
![Docker Hub container registry](images/docker/Docker_Hub_container_registry.png?raw=true "Docker Hub container registry")

*the following images show a screenshot of the adls "storage account", "data" container in which the resulting data was saved
![ADLS stored hotel weather data](images/ADLS/ADLS_stored_hotel_weather_data.png?raw=true "ADLS stored hotel weather data")