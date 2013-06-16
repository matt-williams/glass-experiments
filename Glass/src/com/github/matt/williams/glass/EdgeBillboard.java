package com.github.matt.williams.glass;

import android.content.res.Resources;
import android.graphics.Rect;

import com.github.matt.williams.android.ar.CameraBillboard;
import com.github.matt.williams.android.gl.FragmentShader;
import com.github.matt.williams.android.gl.Program;
import com.github.matt.williams.android.gl.Projection;
import com.github.matt.williams.android.gl.Texture;
import com.github.matt.williams.android.gl.VertexShader;

public class EdgeBillboard extends CameraBillboard {
    public EdgeBillboard(Resources resources, Texture texture) {
        super(new Program(new VertexShader(resources.getString(com.github.matt.williams.android.ar.R.string.cameraBillboardVertexShader)),
                          new FragmentShader(resources.getString(R.string.edgeFragmentShader))),
              texture);
    }

    @Override
    public void render(Projection camera, Projection projection, Rect rect) {
        mProgram.setUniform("duv", 1.0f / (rect.right - rect.left), 1.0f / (rect.bottom - rect.top));
        super.render(camera, projection, rect);
    }
}
