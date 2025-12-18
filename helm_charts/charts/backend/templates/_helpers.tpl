{{/*
=== DEFINIZIONI NOMI (FULLNAME) ===
Queste funzioni generano i nomi univoci per le risorse nel cluster.
Usiamo .Release.Name come base per evitare ripetizioni tipo "teletubbies-prod-teletubbies-prod".
*/}}

{{/* Fullname per il backend */}}
{{- define "backend.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-backend" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/* Fullname per il frontend */}}
{{- define "frontend.fullname" -}}
{{- if .Values.frontend.fullnameOverride -}}
{{- .Values.frontend.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-frontend" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/* Fullname per il componente Database (usato per la ConfigMap dello schema) */}}
{{- define "db.fullname" -}}
{{- printf "%s-db" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
=== ETICHETTE (LABELS) ===
*/}}

{{/* Labels comuni per il Backend */}}
{{- define "backend.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}
{{ include "backend.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- with .Values.global.labels }}
{{ toYaml . }}
{{- end }}
{{- end }}

{{/* Selector labels per il Backend */}}
{{- define "backend.selectorLabels" -}}
app.kubernetes.io/name: teletubbies-app
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/component: backend
{{- end }}

{{/* Labels comuni per il componente DB */}}
{{- define "db.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}
app.kubernetes.io/name: teletubbies-app
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/component: db
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- with .Values.global.labels }}
{{ toYaml . }}
{{- end }}
{{- end }}

{{/* Labels comuni per il Frontend */}}
{{- define "frontend.labels" -}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}
{{ include "frontend.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- with .Values.global.labels }}
{{ toYaml . }}
{{- end }}
{{- end }}

{{/* Selector labels per il Frontend */}}
{{- define "frontend.selectorLabels" -}}
app.kubernetes.io/name: teletubbies-app
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/component: frontend
{{- end }}