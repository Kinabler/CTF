from PIL import Image, ImageChops
im1 = Image.open('img1.png')
im2 = Image.open('img2.png')

im3 = ImageChops.add(ImageChops.subtract(im2, im1), ImageChops.subtract(im1, im2))
im3.show()
im3.save("im3.png")