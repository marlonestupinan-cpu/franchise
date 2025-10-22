# Credentials in Parameter Store
resource "aws_ssm_parameter" "db_user" {
  name  = "/${var.project_name}/${var.environment}/db/username"
  type  = "String"
  value = "root"
}

resource "aws_ssm_parameter" "db_password" {
  name  = "/${var.project_name}/${var.environment}/db/password"
  type  = "SecureString"
  value = "rootrootroot"
}


resource "aws_db_subnet_group" "default" {
  name       = "${var.project_name}-subnet-group"
  subnet_ids = [aws_subnet.private_a.id, aws_subnet.private_b.id]

  tags = {
    Name = "${var.project_name}-rds-subnet-group"
  }
}

resource "aws_db_instance" "default" {
  identifier           = "${var.project_name}-${var.environment}-db"
  allocated_storage    = 20 # GB
  instance_class       = var.db_instance_class
  engine               = "mysql"
  engine_version       = "8.0"

  db_subnet_group_name   = aws_db_subnet_group.default.name
  vpc_security_group_ids = [aws_security_group.rds.id]
  publicly_accessible    = false

  db_name              = "${var.db_schema}"
  username             = aws_ssm_parameter.db_user.value
  password             = aws_ssm_parameter.db_password.value

  skip_final_snapshot  = true
}