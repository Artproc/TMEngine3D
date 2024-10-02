#version 400 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec2 f_textureCoords;


void main()
{

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    f_textureCoords = textureCoords;
}
