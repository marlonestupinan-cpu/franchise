# Spring Boot AWS Deployment with Terraform

This project deploys a reactive Spring Boot application to AWS using Terraform.

## Prerequisites

Before you begin, ensure you have the following tools installed and configured:
* [AWS CLI](https://aws.amazon.com/cli/) (configured with your credentials)
* [Terraform CLI](https://www.terraform.io/downloads.html)
* [Java/JDK](https://www.oracle.com/java/technologies/downloads/) (for Gradle)
* [Docker](https://www.docker.com/products/docker-desktop/) or [Podman](https://podman.io/)

---

## 🚀 Deployment Steps

This deployment follows a two-phase process:
1.  **Phase 1 (Terraform):** Create the base infrastructure, including the empty ECR repository.
2.  **Phase 2 (Docker/AWS CLI):** Build the app and push the Docker image to the new repository.
3.  **Phase 3 (Terraform):** Run `apply` again to update the ECS service with the newly pushed image.

### Step 1: Build the Application and Docker Image

First, build the application JAR and then create the local Docker image.

1.  **Build the application:**
    ```bash
    ./gradlew build
    ```

2.  **Build the Docker/Podman image:**
    *(Use the command for your container tool)*
    ```bash
    # For Docker
    docker build -t my-spring-app:latest .
    
    # For Podman
    podman build -t my-spring-app:latest .
    ```

### Step 2: Deploy Base Infrastructure (Terraform)

Navigate to the `terraform/` directory containing your `.tf` files.

1.  **Initialize Terraform:**
    This downloads the required AWS provider.
    ```bash
    terraform init
    ```

2.  **Apply (Phase 1):**
    This creates all the resources (VPC, RDS, ALB, and the **empty ECR repository**).
    ```bash
    terraform apply
    ```
    Review the plan and type `yes` to approve. This will take several minutes.

### Step 3: Push the Docker Image to ECR

Now that the ECR repository exists, you can push your local image to it.

1.  **Log in to the AWS ECR Registry:**
    Replace `ACOUNT_ID` with your 12-digit AWS Account ID and `us-east-1` with your AWS region.
    ```bash
    # For Docker
    aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ACOUNT_ID.dkr.ecr.us-east-1.amazonaws.com

    # For Podman
    aws ecr get-login-password --region us-east-1 | podman login --username AWS --password-stdin ACOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
    ```

2.  **Tag the Image:**
    Tag your local image (`my-spring-app:latest`) with the full ECR repository URI.
    * **Note:** Replace `ACOUNT_ID` and ensure `springboot-app-repo` matches the `name` you set for the `aws_ecr_repository` resource in your `ecs.tf` file.
    ```bash
    docker tag my-spring-app:latest ACOUNT_[ID.dkr.ecr.us-east-1.amazonaws.com/springboot-app-repo:latest](https://ID.dkr.ecr.us-east-1.amazonaws.com/springboot-app-repo:latest)
    ```

3.  **Push the Image:**
    ```bash
    docker push ACOUNT_[ID.dkr.ecr.us-east-1.amazonaws.com/springboot-app-repo:latest](https://ID.dkr.ecr.us-east-1.amazonaws.com/springboot-app-repo:latest)
    ```

### Step 4: Final Deployment (Terraform)

Run `apply` one more time. Terraform will detect that the ECS service's task isn't running (because the image was missing). It will now launch a new task, which will successfully pull your image from ECR and start the application.

```bash
terraform apply