variable "project_name" {
  type        = string
  default     = "franchise-reactive"
}

variable "environment" {
  type        = string
  default     = "dev"
}

variable "aws_region" {
  type        = string
  default     = "us-east-1"
}

variable "db_instance_class" {
  type        = string
  default     = "db.t3.micro"
}

variable "container_cpu" {
  type        = number
  default     = 256
}

variable "container_memory" {
  type        = number
  default     = 512
}

variable "db_schema" {
  type        = string
  default     = "franchise"
}