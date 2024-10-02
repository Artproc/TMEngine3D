#version 400 core

in vec2 f_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main()
{
    out_Color = texture(textureSampler, f_textureCoords);
}
